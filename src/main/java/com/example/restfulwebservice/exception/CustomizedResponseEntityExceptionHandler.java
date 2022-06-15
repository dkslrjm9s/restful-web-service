package com.example.restfulwebservice.exception;

import com.example.restfulwebservice.user.UserNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;


//ControllerAdvice > AOP 로 사용가능하게 됨.
// AOP : 전체적으로 사용되어야 하는 기능을 명시함.
// 모든 컨트롤러가 실행 될때마다 반드시 아래 컨트롤러가 실행됨 명시
// 오류가 발생할시 아래 컨트롤러에서 가로채서 처리함.
@RestController
@ControllerAdvice
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    // 어디서든 excetpion 이 발생하면 여기로 옴
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));

        return new ResponseEntity(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    // UserNotFoundException 에대한 오류 만 여기서 처리할게
    @ExceptionHandler(UserNotFoundException.class)
    public final ResponseEntity<Object> handleUserNotFoundException(Exception ex, WebRequest request) {
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));

        return new ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    // 부모 클래스인 ResponseEntityExceptionHandler 가 가지고 있는 method를 그대로 가져와 재정의해서 사용함.
    // 오버라이딩
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, // 발생한 exception 객체
                                                                  HttpHeaders headers, //request header
                                                                  HttpStatus status, // 상태
                                                                  WebRequest request) { // 요청값

//        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(),
//                ex.getMessage(),ex.getBindingResult().toString());

        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(),
                "Validation Failed",ex.getBindingResult().toString());

        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
}
