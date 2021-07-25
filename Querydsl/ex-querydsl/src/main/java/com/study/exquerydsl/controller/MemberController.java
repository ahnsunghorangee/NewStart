package com.study.exquerydsl.controller;

import com.study.exquerydsl.dto.MemberSearchCondition;
import com.study.exquerydsl.dto.MemberTeamDto;
import com.study.exquerydsl.repo.MemberJpaRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberJpaRepo memberJpaRepo;

    @GetMapping("/v1/members")
    public List<MemberTeamDto> searchMemberV1(MemberSearchCondition condition){
        return memberJpaRepo.search(condition);
    }
}
