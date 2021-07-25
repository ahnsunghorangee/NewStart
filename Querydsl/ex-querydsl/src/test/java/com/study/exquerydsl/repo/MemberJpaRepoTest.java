package com.study.exquerydsl.repo;

import com.study.exquerydsl.dto.MemberSearchCondition;
import com.study.exquerydsl.dto.MemberTeamDto;
import com.study.exquerydsl.entity.Member;
import com.study.exquerydsl.entity.Team;
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

    @Test
    public void searchTest() throws Exception{
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");

        em.persist(teamA);
        em.persist(teamB);

        Member member1 = new Member("member1", 10, teamA);
        Member member2 = new Member("member2", 20, teamA);
        Member member3 = new Member("member3", 30, teamB);
        Member member4 = new Member("member4", 40, teamB);

        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);

        MemberSearchCondition condition = new MemberSearchCondition();
        condition.setAgeGoe(35);
        condition.setAgeLoe(40);
        condition.setTeamName("teamB");

        List<MemberTeamDto> result = memberJpaRepo.search(condition);

        for (MemberTeamDto memberTeamDto : result) {
            System.out.println("memberTeamDto = " + memberTeamDto);
        }
        assertThat(result).extracting("username").containsExactly("member4");
    }

}