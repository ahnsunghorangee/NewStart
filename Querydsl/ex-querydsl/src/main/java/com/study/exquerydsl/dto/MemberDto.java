package com.study.exquerydsl.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemberDto {

    private String username;
    private int age;

    // DTO를 Q파일 생성 gradle -> other -> compileQuerydsl
    @QueryProjection
    public MemberDto(String username, int age) {
        this.username = username;
        this.age = age;
    }



}
