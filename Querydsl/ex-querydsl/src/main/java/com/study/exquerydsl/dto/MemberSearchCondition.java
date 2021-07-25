package com.study.exquerydsl.dto;

import lombok.Data;

@Data
public class MemberSearchCondition {
    // 회원명, 팀명, 나이(ageGoe, ageLoe) 조건 설정
    private String username;
    private String teamName;
    private Integer ageGoe; // null일 수 있어서 Integer
    private Integer ageLoe;
}
