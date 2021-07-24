package com.study.exquerydsl.repo;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.study.exquerydsl.entity.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static com.study.exquerydsl.entity.QMember.*;

@Repository
public class MemberJpaRepo {

    private final EntityManager em; // JPA에 접근하기 위해 필요
    private final JPAQueryFactory queryFactory; // Querydsl을 쓰기 위해 필요

    // [순수 JPA 리포지토리와 Querydsl] 뒷부분 정리하자
    public MemberJpaRepo(EntityManager em){ // (EntityManager em, JPAQueryFactory queryFactory) queryFactory를 @Bean에 등록하고 사용가능
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
//        this.queryFactory = queryFactory;
        /*
        2가지 방법의 장단점

        1번 방식)
            - 테스트 코드 짜기 수월
        2번 방식)
            - @Bean을 등록하면 @RequiredArgsConstructor를 사용하여 이 부분을 생략할 수 있다.
            - JPAQueryFactory를 외부에서 주입받아 테스트 코드 짤대 불편하다.
         */
    }

    public void save(Member member){
        em.persist(member);
    }

    public Optional<Member> findById(Long id){
        Member findMember = em.find(Member.class, id);
        return Optional.ofNullable(findMember);
    }

    public List<Member> findAll(){
        return em.createQuery("select m from Member m", Member.class).getResultList();
    }

    public List<Member> findAll_Querydsl(){
        return queryFactory
                .selectFrom(member) // QMember.member
                .fetch();
    }

    public List<Member> findByUsername(String username){
        return em.createQuery("select m from Member m where m.username = :username", Member.class)
                .setParameter("username", username)
                .getResultList();
    }

    public List<Member> findByUsername_Querydsl(String username){
        return queryFactory
                .selectFrom(member)
                .where(member.username.eq(username))
                .fetch();
    }

}
