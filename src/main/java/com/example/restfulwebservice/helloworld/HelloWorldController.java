package com.example.restfulwebservice.helloworld;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
public class HelloWorldController {

    // @(어노테이션을 통한 주입)
    // UserControleer.java에서 생성자를 통한 주입을 했다면 이건 @ 이용 (setter method 통해서도 주입가능)
    // @Autowired : 현재 springframework에 등록 되어 있는 Bean들 중 같은 타입을 가지고 있는 Bean을 자동으로 주입해줌.
    @Autowired
    private MessageSource messageSource;


    // 통신 방식 : GET
    // url (endpoing) : /hello-world

    // spring 4.0 이전에는 @RequestMapping(method=RequestMethod.GET, path="/hello-world") 으로 지정함
   @GetMapping(path = "/hello-world")
    public String helloWorld() {
        return "Hello World";
    }

    // alt + enter
    // 마우스 우측 > generate에서도 되긴 해
    @GetMapping(path = "/hello-world-bean")
    public HelloWorldBean helloWorldBean() {
       // string이 아닌 java-bean 형태로 반환하게 되면 RestController에서 자동으로 데이터를 json으로 바꿈
        // response body 에 저장하지 않더라도
       // 만약 xml 형태로 반환 하고 싶다면??
        return new HelloWorldBean("Hello World");
    }

    // path variable을 사용하여 가변데이터 url 만들기
    // {} 과 method의 인자명은 같아야 함.
    // 만약 다르게 지정하고 싶다면 helloWorldBean(@PathVariable(value = "name") String name2)
   @GetMapping(path = "/hello-world-bean/path-variable/{name}")
    public HelloWorldBean helloWorldBean(@PathVariable String name) {
        return new HelloWorldBean(String.format("Hello World, %s",name));
    }

    // 다국어 처리
    // request header를 통해 Locale 입력받음
    // 필수인지 아닌지는 required
    // accpet-language가 안들어온다며 디폴트 locale 값인 한국어가 실행됨
    @GetMapping(path="/hello-world-internationalized")
    public String helloWorldInternationalized(
            @RequestHeader(name="Accept-Language", required = false) Locale locale) {
       // messageSource.getMessage(우리가 만들었던 번들(messages.properties)에서 어떠한 키값을 가지고 올것인지,
        // 그리고 그 키값이 파라미터(가변변수)를 가지고 있는 문자열 이라면 그 문자열을 채우기위한 인자, locale 값)
       return messageSource.getMessage("greeting.message",null,locale);
    }
}
