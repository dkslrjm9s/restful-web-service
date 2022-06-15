package com.example.restfulwebservice.user;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;


// AllArgsConstructor 모든 필드를 다가지고 있는 생성자 자동 생성
// @DataNoArgsConstructor 디폴트 생성자 생성
// -> public User() {}
@Data
@AllArgsConstructor
//@JsonIgnoreProperties(value = {"password","ssn"})
//@JsonFilter("UserInfo") // Hateos 때문에 잠시 주석
@ApiModel(description = "사용자 상세 정보를 위한 도메인 객체") // swagger로 만든 문서에 상세 설명 부여
@NoArgsConstructor // 이 클래스를 상속하고자 하는 클래스에서 없으면 오류남. 디폴트 생성자가 없어서
public class User {
    private Integer id;

    @Size(min=2, message = "Name은 2글자 이상 입력해 주세요.")
    @ApiModelProperty(notes = "사용자 이름을 입력해 주세요.") // swagger로 만든 문서에 상세 설명 부여
    private String name;
    @Past
    @ApiModelProperty(notes = "사용자 등록일을 입력해 주세요.") // swagger로 만든 문서에 상세 설명 부여
    private Date joinDate;

    // Response 데이터 제어(Filtering)
    // @JsonIgnore : 변수 하나에 대해 적용됨. request가 와도 Response 하지않음
    // @JsonIgnoreProperties : 클래스에 대해 적용됨. 숨기고 싶은 변수의 이름을 객체로 전달.
    // @JsonFilter : 필터를 하나 생성함(인자는 filter name), 생성된 filter는 컨트롤러 혹은 서비스 클래스에서 사용됨.
    //               JsonFilter를 사용하면 이 클래서에 filter가 걸려있는 거임. 이 클래스는 UserInfo 라는 필터가 걸려 있는 것.
    //               UserInfo 를 사용하지 않은채 바로 data를 호출하면 오류가 발생함.

    //@JsonIgnore
    @ApiModelProperty(notes = "사용자 패스워드를 입력해 주세요.") // swagger로 만든 문서에 상세 설명 부여
    private String password;
    @ApiModelProperty(notes = "사용자 주민번호를 입력해 주세요.") // swagger로 만든 문서에 상세 설명 부여
    private String ssn;

}

