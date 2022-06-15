package com.example.restfulwebservice.helloworld;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//Spring에서 선언되어 관리되고 있는 인스턴스를 우리는 Bean 이라고 부르기로 함.
// 그용도에 따라 Controller Bean / Service Bean / Repository Bean 과 같이 선언해서 사용함.


// lombok : 빈클래스를 만들면 getter, setter, tostring, equal 등 method를 자동생성
// @Data 추가해주면 lombok이 HelloWorldBean 가지고 있는 모든 프로퍼티에 대해 setter,getter, tostring 등을 만들어줌
// @AllArgsConstructor 추가해주면 lombok이 모든 argument를 가지고 있는 생성자(constructor)를 만듬
//  > 단 이걸 쓰면 따로 내 맘대로 생성자를 만들진 못해. 동일한 생성자를 두 번쓰진 못하니까
// 만약 존재하는 모든 프로퍼티에 해당하는 생성자 말고 그냥 디폴트 생성자만 추가하고 싶다면
// @NoArgsConstructor 이거 써(HelloWorldBean() 이거 생김)

// 전체적인 구조 보려면 Structure에서 봐 소스에서 못보니까ㅜ

// 단, lombok을 쓰려면 settings > Builds,Execution,Deployment > Compiler > Annotation Processors 에서 Enable annotation processing 옵션 체크 해줘야 함.
// 프로젝트별 설정이라 매번 바꿔주어야 한뎅
// 아니면 그냥 돌려 오류나 그럼 힌트 눌러

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HelloWorldBean {
    private String message;

    // lombok @Data 를 해주면 아래처럼 안해줘도 됭
//    //getter
//    public String getMessage() {
//        return this.message;
//    }
//    // setter
//    public void setMessage(String msg) {
//        this.message = msg
//    }

    // lombok @AllArgsConstructor 를 해주면 아래처럼 안해줘도 됭
//    public HelloWorldBean(String message) {
//        this.message = message;
//    }
}
