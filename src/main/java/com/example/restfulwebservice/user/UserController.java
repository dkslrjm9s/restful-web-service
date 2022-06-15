package com.example.restfulwebservice.user;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class UserController {
    // Spring에서는 직접 new를 이용해서 객체를 할당 아니라 의존성 주입(DI) 방법 사용


    // Spring에서 선언되어 관리되고 있는 인스턴스를 우리는 Bean 이라고 부르기로 함.
    // 아래의 UserDaoServic도 Bean
    // 그용도에 따라 Controller Bean / Service Bean / Repository Bean 과 같이 선언해서 사용함.
    // Spring은 이렇게 선언된 Bean 을 의존성 주입을 통해 관리함.
    // DI는 xml설정 파일을 통해서도 가능하고 class에서 setter,method,생성자를 통해서도 가능함.
    // 즉, Spring Container에 등록된 Bean들을 사용하기 위해서는 그 참조값을 가져와서 사용
    // Spring Container 혹은 IOC Container에 등록된 Bean 은 개발자가 프로그램 실행 도중에 변경할수 없어 일괄성 있는 인스턴스를 사용할수 있게됨.

    private UserDaoService service; // 일반 클래스가 아닌 어떤 용도로 사용될지 명시해야함.

    public UserController(UserDaoService service) {
        this.service = service; // 의존성 주입
    }

    @GetMapping("/users")
    public List<User> retrieveAllUsers() {
        return service.findAll();
    }

    //get /users/1 or /users/10 -> String (하지만 인자는 자동 맵핑됨)
    @GetMapping("/users/{id}")
    public EntityModel<User> retrieveUser(@PathVariable int id) {
        User user = service.findOne(id);
    
        // 예외 처리
        if(user == null) {
            throw new UserNotFoundException(String.format("ID[%S] not found", id));
        }

        // HATEOAS
        // EntityModel 객체 생성
        EntityModel<User> resource = EntityModel.of(user);
        // 추가적인 정보, link를 넣어놈
        // 이클래스가 가지고 있는 것 중에서 retrieveAllUsers을 연동
        // linkTo, methodOn import함. 위를 봐
        WebMvcLinkBuilder linkTo = linkTo(
                methodOn(this.getClass()).retrieveAllUsers()
        );
        // 위에서 생성한 link를 all-users라는 이름으로 연결결
       resource.add(linkTo.withRel("all-users"));

        return resource;
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        User savedUser = service.save(user);

        // 응답코드 제어
        // GET 과 POST 방식을 가진 동일 url 이있음. 두개를 구분 짓기 위해 http 응답코드를 제어해봄.

        // ServletUriComponentsBuilder 클래스 사용
        // 현재 가지고 있는 request값 사용함 : fromCurrentRequest
        // 반환시켜주고자 할때의 path값 : .path("/{id}")
        // .path("/{id}") 로 설정했던 id(가변변수) 에 값을 지정 : .buildAndExpand(savedUser.getId())
        // 이 모든 형태를 Uri 형태로 변환 : .toUri()
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();
        
        //=> loacation url이 전달되고 201 응답코드가 반환됨.
        // crud 고려 안하고 사용자의 모든 요청을 post 로 200 번 응답코드 내는게 제일 안좋은 케이스야
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id) {
        User user = service.deleteById(id);

        if(user == null) {
            throw new UserNotFoundException(String.format("ID[%S] not found", id));
        }
    }

    @PutMapping("/users")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        User savedUser = service.update(user);

        if(savedUser == null) {
            throw new UserNotFoundException(String.format("ID[%S] not found", user.getId()));
        }

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }
}
