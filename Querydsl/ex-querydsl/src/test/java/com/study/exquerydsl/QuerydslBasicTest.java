package com.study.exquerydsl;

import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.study.exquerydsl.entity.Member;
import com.study.exquerydsl.entity.QMember;
import com.study.exquerydsl.entity.QTeam;
import com.study.exquerydsl.entity.Team;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

import static com.querydsl.jpa.JPAExpressions.*;
import static com.study.exquerydsl.entity.QMember.*;
import static com.study.exquerydsl.entity.QTeam.team;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
public class QuerydslBasicTest {

    @Autowired
    EntityManager em;

    JPAQueryFactory queryFactory;

    @BeforeEach
    public void before() {
        queryFactory = new JPAQueryFactory(em);
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
    }

    @Test
    public void startJPQL() {
        Member findMember = em.createQuery("select m from Member m where m.username = :username", Member.class)
                .setParameter("username", "member1") // 파라미터 바인딩 o
                .getSingleResult();

        assertThat(findMember.getUsername()).isEqualTo("member1");
    }

    @Test
    public void startQuerydsl() {
        Member findMember = queryFactory
                .select(member)
                .from(member)
                .where(member.username.eq("member1")) // 파라미터 바인딩 x
                .fetchOne();

        assertThat(findMember.getUsername()).isEqualTo("member1");
    }

    /* 검색 조건 쿼리 */
    @Test
    public void search() {
        Member findMember = queryFactory
                .selectFrom(member)
                .where(member.username.eq("member1")
                        .and(member.age.eq(10)))
                .fetchOne();

        assertThat(findMember.getUsername()).isEqualTo("member1");
    }

    @Test
    public void searchAndParam() { // .and를 ,로 사용 가능
        Member findMember = queryFactory
                .selectFrom(member)
                .where(
                        member.username.eq("member1"),
                        member.age.eq(10)
                )
                .fetchOne();

        assertThat(findMember.getUsername()).isEqualTo("member1");
    }

    /* 결과 조회 fetch() */
    @Test
    public void resultFetch() {

        List<Member> fetch = queryFactory
                .selectFrom(member)
                .fetch();

        Member member = queryFactory
                .selectFrom(QMember.member)
                .fetchOne();

        Member member1 = queryFactory
                .selectFrom(QMember.member)
                .fetchFirst();

        // 페이징 용 쿼리, totalcount를 위해 쿼리를 2번(count / select) 날린다.
        QueryResults<Member> results = queryFactory
                .selectFrom(QMember.member)
                .fetchResults(); // fetchResults(): contents용 쿼리 (2방, contents / select)
        // count쿼리를 원할 때는 따로 작성하는 것이 효율적
        // 왜? 이거는 쿼리가 2방 나가서 복잡하면 성능 저하

        results.getTotal();
        List<Member> content = results.getResults();

        long total = queryFactory
                .selectFrom(QMember.member)
                .fetchCount();
    }

    /* 정렬 */

    /**
     * 회원 정렬 순서
     * 1. 회원 나이 내림차순 (desc)
     * 2. 회원 이름 올림차순 (asc)
     * 단, 2에서 회원 이름이 없으면 마지막에 출력 (nulls last)
     */
    @Test
    public void sort() {
        em.persist(new Member(null, 100));
        em.persist(new Member("member5", 100));
        em.persist(new Member("member6", 100));

        List<Member> result = queryFactory
                .selectFrom(member)
                .where(member.age.eq(100))
                .orderBy(member.age.desc(), member.username.asc().nullsLast()) // nullFirst()도 존재
                .fetch();

        Member member5 = result.get(0);
        Member member6 = result.get(1);
        Member memberNull = result.get(2);
        assertThat(member5.getUsername()).isEqualTo("member5");
        assertThat(member6.getUsername()).isEqualTo("member6");
        assertThat(memberNull.getUsername()).isNull();
    }

    /* 페이징 */
    @Test
    public void paging() {
        // offset
        List<Member> result = queryFactory
                .selectFrom(member)
                .orderBy(member.username.desc())
                .offset(1) // 앞에 몇 개를 스킵할 것인가
                .limit(2)
                .fetch();

        for (Member m : result) {
            System.out.println(m.toString());
        }

        assertThat(result.size()).isEqualTo(2);

        // 전체 조회수
        QueryResults<Member> queryResults = queryFactory
                .selectFrom(member)
                .orderBy(member.username.desc())
                .offset(1)
                .limit(2)
                .fetchResults(); // fetchResults(): contents용 쿼리 (2방, contents / select)
        // count쿼리를 원할 때는 따로 작성하는 것이 효율적
        // 왜? 이거는 쿼리가 2방 나가서 복잡하면 성능 저하

        assertThat(queryResults.getTotal()).isEqualTo(4);
        assertThat(queryResults.getLimit()).isEqualTo(2);
        assertThat(queryResults.getOffset()).isEqualTo(1);
        assertThat(queryResults.getResults().size()).isEqualTo(2);
    }

    /* 집합 */
    @Test
    public void aggregation() {
        List<Tuple> result = queryFactory
                .select(
                        member.count(),
                        member.age.sum(),
                        member.age.avg(),
                        member.age.max(),
                        member.age.min()
                )
                .from(member)
                .fetch();
        // 이처럼, select에서 원하는 값을 찍으면 Tuple로 조회한다.

        // 데이터를 꺼낼때, 다음과 같이 꺼내야한다.
        Tuple tuple = result.get(0); // 0번째에 한꺼번에 들어있다.
        System.out.println("Tuple: " + tuple);

        // tuple에서 꺼낼 때 member.count()처럼 꺼내면 된다.
        assertThat(tuple.get(member.count())).isEqualTo(4);
        assertThat(tuple.get(member.age.sum())).isEqualTo(100);
        assertThat(tuple.get(member.age.avg())).isEqualTo(25);
        assertThat(tuple.get(member.age.max())).isEqualTo(40);
        assertThat(tuple.get(member.age.min())).isEqualTo(10);

        // Tuple을 쓰는 이유는 데이터 타입이 다양해서
        // 실무에서는 DTO로 꺼내온다. 참고만 하자.
    }

    /**
     * 팀의 이름과 각 팀의 평균 연령을 구해라
     *
     * @throws Exception
     */
    @Test
    public void group() throws Exception {
        List<Tuple> result = queryFactory
                .select(team.name, member.age.avg())
                .from(member)
                .join(member.team, team) // member의 team과 team 조인
                .groupBy(team.name)
//                .having() // having도 가능
                .fetch();

        // Team이 2개이고 groupBy로 인해 결과가 2개일 것이다.
        Tuple teamA = result.get(0);
        Tuple teamB = result.get(1);

        assertThat(teamA.get(team.name)).isEqualTo("teamA");
        assertThat(teamA.get(member.age.avg())).isEqualTo(15);

        assertThat(teamB.get(team.name)).isEqualTo("teamB");
        assertThat(teamB.get(member.age.avg())).isEqualTo(35);
    }


    /* 조인 - 기본 조인 */
    // 조인의 기본 문법은
    // 첫번째 파라미터에 조인 대상 지정
    // 두번째 파라미터에 별칭으로 사용할 Q 타입을 지정

    /**
     * TeamA에 소속된 모든 회원
     *
     * @throws Exception
     */
    @Test
    public void join() throws Exception {
        List<Member> result = queryFactory
                .selectFrom(member)
                .join(member.team, team) // 여기서 team은 QTeam.team / leftJoin, rightJoin 모두 가능
                .where(team.name.eq("teamA"))
                .fetch();

        assertThat(result)
                .extracting("username")
                .containsExactly("member1", "member2");
    }

    /**
     * 연관관계 없이 조인 가능! theta join
     * <p>
     * 회원의 이름이 팀 이름과 같은 회원 조회
     *
     * @throws Exception
     */
    @Test
    public void theta_join() throws Exception {
        em.persist(new Member("teamA"));
        em.persist(new Member("teamB"));
        em.persist(new Member("teamC"));

        List<Member> result = queryFactory
                .select(member)
                .from(member, team)
                .where(member.username.eq(team.name))
                .fetch();

        assertThat(result)
                .extracting("username")
                .containsExactly("teamA", "teamB");

        // 단 1가지 제약, 외부(outer) 조인이 불가능하다.
    }


    /* 조인 - on절 */
    /*
    on절을 활용한 조인 2가지 방법
    1) 조인 대상 필터링
     */

    /**
     * 회원과 팀을 조인할 때, 팀 이름이 teamA인 팀만 조인, 회원은 모두 조회
     * JPQL: select m, t
     * from Member m
     * left join m.team t
     * on t.name = 'teamA'
     *
     * @throws Exception
     */
    @Test
    public void join_on_filtering() throws Exception {
        List<Tuple> result = queryFactory
                .select(member, team)
                .from(member)
                .leftJoin(member.team, team)
                .on(team.name.eq("teamA"))
                .fetch();

        for (Tuple tuple : result) {
            System.out.println("tuple = " + tuple);
        }
    }

    /*
    on절을 활용한 조인 2가지 방법
    2) 연관관계 없는 엔티티 외부조인
     */

    /**
     * 회원의 이름이 팀 이름과 같은 대상 외부 조인
     *
     * @throws Exception
     */
    @Test
    public void join_on_no_relation() throws Exception {
        em.persist(new Member("teamA"));
        em.persist(new Member("teamB"));
        em.persist(new Member("teamC"));

        List<Tuple> result = queryFactory
                .select(member, team)
                .from(member)
                .leftJoin(team).on(member.username.eq(team.name))
                .fetch();

        for (Tuple tuple : result) {
            System.out.println("tuple: " + tuple);
        }
    }

    /* 조인 - 패치 조인 */
    /*
    패치 조인은 SQL에서 제공하는 기능은 아니다.
    SQL 조인을 활용해서 연관된 엔티티를 SQL 한번에 조회하는 기능
    주로 성능 최적화에 사용하는 방법이다.
     */
    @PersistenceUnit
    EntityManagerFactory emf;

    @Test
    public void fetchJoinNo() throws Exception { // 패치 조인이 없을 때
        em.flush();
        em.clear();

        Member findMember = queryFactory
                .selectFrom(QMember.member)
                .where(QMember.member.username.eq("member1"))
                .fetchOne();

        boolean loaded = emf.getPersistenceUnitUtil().isLoaded(findMember.getTeam());
        assertThat(loaded).as("패치 조인 미적용").isFalse();
    }

    @Test
    public void fetchJoinUse() throws Exception { // 패치 조인 적용
        em.flush();
        em.clear();

        Member findMember = queryFactory
                .selectFrom(QMember.member)
                .join(member.team, team).fetchJoin()
                .where(QMember.member.username.eq("member1"))
                .fetchOne();

        boolean loaded = emf.getPersistenceUnitUtil().isLoaded(findMember.getTeam());
        assertThat(loaded).as("패치 조인 미적용").isTrue();
    }

    /* 서브 쿼리 */
    // com.querydsl.jpa.JPAExpressions 사용

    /**
     * 나이가 가장 많은 회원 조회
     *
     * @throws Exception
     */
    @Test
    public void subQuery() throws Exception {
        // 서브쿼리에서 사용하는 memebr가 겹치면 안되므로
        QMember memberSub = new QMember("memberSub");

        List<Member> result = queryFactory
                .selectFrom(member)
                .where(member.age.eq(
                        select(memberSub.age.max())
                                .from(memberSub)
                ))
                .fetch();

        assertThat(result).extracting("age").containsExactly(40);
    }

    /**
     * 나이가 평균 이상인 회원
     *
     * @throws Exception
     */
    @Test
    public void subQueryGoe() throws Exception {
        // 서브쿼리에서 사용하는 memebr가 겹치면 안되므로
        QMember memberSub = new QMember("memberSub");

        List<Member> result = queryFactory
                .selectFrom(member)
                .where(member.age.goe(
                        select(memberSub.age.avg())
                                .from(memberSub)
                ))
                .fetch();

        assertThat(result).extracting("age").containsExactly(30, 40);
    }

    /**
     * 나이가 평균 이상인 회원
     *
     * @throws Exception
     */
    @Test
    public void subQueryIn() throws Exception {
        // 서브쿼리에서 사용하는 memebr가 겹치면 안되므로
        QMember memberSub = new QMember("memberSub");

        List<Member> result = queryFactory
                .selectFrom(member)
                .where(member.age.in(
                        select(memberSub.age)
                                .from(memberSub)
                                .where(memberSub.age.gt(10))
                ))
                .fetch();

        assertThat(result).extracting("age").containsExactly(20, 30, 40);
    }

    @Test
    public void selectSubQuery() throws Exception {
        QMember memberSub = new QMember("memberSub");

        List<Tuple> result = queryFactory
                .select(member.username,
                        select(memberSub.age.avg()) // JPAExpressions: static import 가능
                                .from(memberSub))
                .from(member)
                .fetch();

        Optional.ofNullable(result).ifPresent(System.out::println);
    }

    /*
    JPA JPQL 서브쿼리의 한계점으로 from 절의 서브쿼리(인라인 뷰)는 지원하지 않는다.
    당연히 Querydsl도 지원하지 않는다.

    해결방법
    1. 서브쿼리를 join으로 변경한다. (가능할 떄도 있고 불가능 할 때도 있다)
    2. 애플리케이션에서 쿼리를 2번 분리해서 실행한다.
    3. nativeSQL을 사용한다.
    */

    /* Case문 */
    @Test
    public void basicCaseTest() throws Exception {
        List<String> result = queryFactory
                .select(member.age
                        .when(10).then("열살")
                        .when(20).then("스무살")
                        .otherwise("기타")
                )
                .from(member)
                .fetch();

        Optional.ofNullable(result).ifPresent(System.out::println);
    }

    @Test
    public void complexCaseTest() throws Exception {
        List<String> result = queryFactory
                .select(new CaseBuilder()
                        .when(member.age.between(0, 20)).then("0~20살")
                        .when(member.age.between(21, 30)).then("21~30살")
                        .otherwise("기타")
                )
                .from(member)
                .fetch();

        Optional.ofNullable(result).ifPresent(System.out::println);
    }

    /* 상수, 문자 더하기 */
    // 상수 더하기
    @Test
    public void constantTest() throws Exception {
        List<Tuple> result = queryFactory
                .select(member.username, Expressions.constant("A"))
                .from(member)
                .fetch();

        for (Tuple tuple : result) {
            System.out.println("tuple: " + tuple);
        }
    }

    // 문자 더하기
    @Test
    public void concat() throws Exception {
        // {username}_{age}
        // age는 자료형이여서 문자열로 바꿔줘야한다. -> .stringValue()
        List<String> result = queryFactory
                .select(member.username.concat("_").concat(member.age.stringValue()))
                .from(member)
                .where(member.username.eq("member1"))
                .fetch();

        for (String s : result) {
            System.out.println("S: " + s);
        }
    }


}
