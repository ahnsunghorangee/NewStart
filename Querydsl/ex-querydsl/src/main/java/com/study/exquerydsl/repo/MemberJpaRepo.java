package com.study.exquerydsl.repo;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.study.exquerydsl.dto.MemberSearchCondition;
import com.study.exquerydsl.dto.MemberTeamDto;
import com.study.exquerydsl.dto.QMemberTeamDto;
import com.study.exquerydsl.entity.Member;
import com.study.exquerydsl.entity.QTeam;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static com.study.exquerydsl.entity.QMember.*;
import static com.study.exquerydsl.entity.QTeam.*;
import static org.springframework.util.StringUtils.*;

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

    // 동적쿼리 성능 최적화 조회 - Builder 사용,DTO로 조회
    public List<MemberTeamDto> searchByBuilder(MemberSearchCondition condition){

        BooleanBuilder builder = new BooleanBuilder();

        // StringUtils.hasText() : static import
        if(hasText(condition.getUsername())){ // username이 null일수도 있고 ""일수도 있어서 이를 방지하기위해 hasText()
            builder.and(member.username.eq(condition.getUsername()));
        }

        if(hasText(condition.getTeamName())){
            builder.and(team.name.eq(condition.getTeamName()));
        }

        if(condition.getAgeGoe() != null){
            builder.and(member.age.goe(condition.getAgeGoe()));
        }

        if(condition.getAgeLoe() != null){
            builder.and(member.age.loe(condition.getAgeLoe()));
        }

        return queryFactory
                .select(new QMemberTeamDto(
                        member.id.as("memberId"),
                        member.username,
                        member.age,
                        team.id.as("teamId"),
                        team.name.as("teamName")
                ))
                .from(member)
                .leftJoin(member.team, team)
                .where(builder)
                .fetch();
    }

    // 동적쿼리 성능 최적화 조회 - Where절 파라미터 이용, DTO로 조회, 위보다 가독성!
    public List<MemberTeamDto> search(MemberSearchCondition condition){
        return queryFactory
                .select(new QMemberTeamDto(
                        member.id.as("memberId"),
                        member.username,
                        member.age,
                        team.id.as("teamId"),
                        team.name.as("teamName")
                ))
                .from(member)
                .leftJoin(member.team, team)
                .where(
                        usernameEq(condition.getUsername()),
                        teamNameEq(condition.getTeamName()),
                        ageGoe(condition.getAgeGoe()),
                        ageLoe(condition.getAgeLoe())
                )
                .fetch();
    }

    private BooleanExpression usernameEq(String username) {
        return hasText(username) ?  member.username.eq(username) : null;
    }

    private BooleanExpression teamNameEq(String teamName) {
        return hasText(teamName) ? team.name.eq(teamName) : null;
    }

    private BooleanExpression ageGoe(Integer ageGoe) {
        return ageGoe != null ? member.age.goe(ageGoe) : null;
    }

    private BooleanExpression ageLoe(Integer ageLoe) {
        return ageLoe != null ? member.age.loe(ageLoe) : null;
    }
}
