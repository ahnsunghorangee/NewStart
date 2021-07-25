package com.study.exquerydsl.repo;

import com.study.exquerydsl.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// 스프링 데이터 JPA 리포지토리로 변경
public interface MemberRepo extends JpaRepository<Member, Long>, MemberRepoCustom {
    List<Member> findByUsername(String username);
}
