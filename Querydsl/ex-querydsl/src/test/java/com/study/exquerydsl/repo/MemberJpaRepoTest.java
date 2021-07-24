package com.study.exquerydsl.repo;

import com.study.exquerydsl.entity.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class MemberJpaRepoTest {

    @Autowired
    EntityManager em;

    @Autowired
    MemberJpaRepo memberJpaRepo;

    @Test
    public void basicTest() throws Exception{
        Member member = new Member("member1", 10);
        memberJpaRepo.save(member);

        Member findMember = memberJpaRepo.findById(member.getId()).get();
        assertThat(findMember).isEqualTo(member);

        List<Member> result1 = memberJpaRepo.findAll();
        assertThat(result1).containsExactly(member);

        List<Member> result2 = memberJpaRepo.findByUsername("member1");
        assertThat(result2).containsExactly(member);
    }

    @Test
    public void basicQuerydslTest() throws Exception{
        Member member = new Member("member1", 10);
        memberJpaRepo.save(member);

        Member findMember = memberJpaRepo.findById(member.getId()).get();
        assertThat(findMember).isEqualTo(member);

        List<Member> result1 = memberJpaRepo.findAll_Querydsl();
        assertThat(result1).containsExactly(member);

        List<Member> result2 = memberJpaRepo.findByUsername_Querydsl("member1");
        assertThat(result2).containsExactly(member);
    }
}