package com.example.restfulwebservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

@SpringBootApplication
public class RestfulWebServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestfulWebServiceApplication.class, args);
    }

    // 다국어처리가 가능한 Bean을 SpringBootApplication에 등록해 스프링부트가 초기화 될때
    // 같이 메모리에 올라가서 다른쪽에 있는 클래스에서 사용할수 있게 함.
    // 다국어처리를 하기위해 다국어 파일명을 저장해야함. -> apllication.properties or yml 에 우리가 사용할 파일의 이름 지정.
    @Bean
    public LocaleResolver localeResolver() {
        // 세션을 통해 locale값을 얻어올것
        SessionLocaleResolver localeResolver = new SessionLocaleResolver();
        //localeresolver에 디폴트값 지정
        localeResolver.setDefaultLocale(Locale.KOREA);
        return localeResolver;
    }

}
