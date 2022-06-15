package com.example.restfulwebservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    // http://localhost:8088/v2/api-docs 에서 확인
    // http://localhost:8088/swagger-ui/index.html
    // 기본정보들 추가하는 부분.
    private static final Contact DEFAULT_CONTACT = new Contact("Kenneth Lee",
            "http://www.joneconsulting.co.kr", "edowon@joneconsulting.co.kr");

    private static final ApiInfo DEFAULT_API_INFO = new ApiInfo("Awesome API Title",
            "My User management REST API service", "1.0","urn:tos",
            DEFAULT_CONTACT,"Apache 2.0", "http://www.apache.org/licenses/LICENSE-2.0", new ArrayList<>());

    private static final Set<String> DEFAULT_PRODUCES_AND_CONSUMES = new HashSet<>(
            Arrays.asList("application/json","application/xml"));

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(DEFAULT_API_INFO)
                .produces(DEFAULT_PRODUCES_AND_CONSUMES)
                .consumes(DEFAULT_PRODUCES_AND_CONSUMES);
    }
}
