package com.example.restfulwebservice.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//HTTP Status code
// 2xx -> OK(정상처리)
// 4xx -> 클라이언트 오류(resorce 잘못 요청, 존재하지 않는 method 요청 등등)
// 5xx -> 서버 오류
@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message); // 부모클래스로 메세지 던짐
    }
}
