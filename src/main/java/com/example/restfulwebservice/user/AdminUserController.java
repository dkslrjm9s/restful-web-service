package com.example.restfulwebservice.user;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

// 좀더 critical 한 역할을 할수 있는 관리자 유저 컨트롤러
// @RequestMapping : 모든 api, method가 공통적으로 가지고 있는 이름(url, 접두사)이 있는 경우 그런 이름을 class 단위로 설정가능
@RestController
@RequestMapping("/admin")
public class AdminUserController {

    private UserDaoService service;

    public AdminUserController(UserDaoService service) {
        this.service = service; // 의존성 주입
    }

//    @GetMapping("/admin/users")
    @GetMapping("/users")
    public MappingJacksonValue retrieveAllUsers() {
        // 바로 호출이 아닌, 변수를 선언한 상태로 분리하고 싶을때?
        // 1. 함수명에 마우스
        // 2-1. ctrl+alt_v
        // 2-2. 마우스 우클릭 > refactor > introduce variable
        List<User> users = service.findAll();

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                .filterOutAllExcept("id","name","joinDate","password");

        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo",filter);

        MappingJacksonValue mapping = new MappingJacksonValue(users);
        mapping.setFilters(filters);

        //return users;
        return mapping;
    }


//    @GetMapping("/admin/users/{id}")

    // 버전부여
    // GET /admin/users/1 -> /admin/v1/users/1
    //@GetMapping("/v1/users/{id}")

    // request paramater를 이용한 버전 관리
    //params : request parameter, url 뒤에 올 값이므로 value 마지막에 꼭 / 붙이자
    //@GetMapping(value = "/users/{id}/", params = "version=1")

    // heaer을 이용한 버전 관리
    //@GetMapping(value = "/users/{id}", headers = "X-API-VERSION=1")

    // MIME type을 이용한 버전 관리
    @GetMapping(value = "/users/{id}", produces = "application/vnd.company.appv1+json")
    public MappingJacksonValue retrieveUserV1(@PathVariable int id) {
        User user = service.findOne(id);
    
        // 예외 처리
        if(user == null) {
            throw new UserNotFoundException(String.format("ID[%S] not found", id));
        }

        // Bean 의 Properties를 제어하기위해 SimpleBeanPropertyFilter 사용.
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                .filterOutAllExcept("id", "name", "joinDate", "ssn"); // response에 포함되고자하는 값

        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo", filter); // 필터 생성

        // client에 반환하는 타입을 user가 아닌 filter로 반환해도 잘 받게 하기위해
        // mappingjacksonvalue를 이용해 데이터 변환하여 전달.

        MappingJacksonValue mapping = new MappingJacksonValue(user);
        mapping.setFilters(filters);

        //return user;
        return mapping;
    }

    //@GetMapping("/v2/users/{id}")
    //@GetMapping(value = "/users/{id}/", params = "version=2")
    //@GetMapping(value = "/users/{id}", headers = "X-API-VERSION=2")
    @GetMapping(value = "/users/{id}", produces = "application/vnd.company.appv2+json")
    public MappingJacksonValue retrieveUserV2(@PathVariable int id) {
        User user = service.findOne(id);

        // 예외 처리
        if(user == null) {
            throw new UserNotFoundException(String.format("ID[%S] not found", id));
        }

        //User -> User2
        UserV2 userV2 = new UserV2();

        // BeanUtils : SpringFramework에서 제공, Bean 들간에 필요한 기능 담겨 있음.
        // 인스턴스 생성, 복사, 두 인스턴스간의 공통 필드 copy 등...
        // copyProperties : user 를 userV2로 복사
        BeanUtils.copyProperties(user, userV2);
        userV2.setGrade("VIP");


        // Bean 의 Properties를 제어하기위해 SimpleBeanPropertyFilter 사용.
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                .filterOutAllExcept("id", "name", "joinDate", "grade"); // response에 포함되고자하는 값

        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfoV2", filter); // 필터 생성

        // client에 반환하는 타입을 user가 아닌 filter로 반환해도 잘 받게 하기위해
        // mappingjacksonvalue를 이용해 데이터 변환하여 전달.

        MappingJacksonValue mapping = new MappingJacksonValue(userV2);
        mapping.setFilters(filters);

        //return user;
        return mapping;
    }
}
