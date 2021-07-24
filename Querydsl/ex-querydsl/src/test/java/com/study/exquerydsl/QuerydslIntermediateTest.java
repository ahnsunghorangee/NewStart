package com.study.exquerydsl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryFactory;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.study.exquerydsl.dto.MemberDto;
import com.study.exquerydsl.dto.QMemberDto;
import com.study.exquerydsl.dto.UserDto;
import com.study.exquerydsl.entity.Member;
import com.study.exquerydsl.entity.QMember;
import com.study.exquerydsl.entity.Team;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import java.util.List;

import static com.querydsl.jpa.JPAExpressions.*;
import static com.study.exquerydsl.entity.QMember.*;
import static com.study.exquerydsl.entity.QTeam.team;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
public class QuerydslIntermediateTest {

    @Autowired
    EntityManager em;

    JPAQueryFactory queryFactory;

    @PersistenceUnit
    EntityManagerFactory emf;

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

    /* ==== 프로젝션 ==== */
    /* 
    프젝젝션: select 대상 지정
    - 프로젝션 대상이 하나면 타입을 명확하게 지정할 수 있음
    - 프로젝션 대상이 둘 이상이면 튜플이나 DTO로 조회
    
    튜플은 Repo 안에서만 쓰고 Service로 DTO로 변환해서 보낸다.
     */

    // 프로젝션 대상이 하나일 때때
    @Test
    public void simpleProjection() throws Exception {
        List<String> result = queryFactory
                .select(member.username)
                .from(member)
                .fetch();

        for (String s : result) {
            System.out.println("S: " + s);
        }
    }

    // 프로젝션 대상이 둘 이상일 때
    @Test
    public void tupleProjection() throws Exception {
        List<Tuple> result = queryFactory
                .select(member.username, member.age)
                .from(member)
                .fetch();

        for (Tuple tuple : result) {
            String username = tuple.get(member.username);
            Integer age = tuple.get(member.age);

            System.out.println("username : " + username);
            System.out.println("age : " + age);
        }
    }

    /* ==== 프로젝션 결과 반환 - DTO 조회 ==== */

    // JPQL 스타일
    /*
    문제점
    1. 순수JPA에서 DTO를 조회할 때는 new 명령어를 사용해야한다
    2. DTO의 package이름을 다 적어줘야해서 지저분하다.
    3. 생성자 방식만 지원한다.
     */
    @Test
    public void findDto() throws Exception {
        // DTO에 들어있는 형태로 DB를 조회하고 싶을 때

        // JPQL - new Operation 문법
        List<MemberDto> resultList = em.createQuery("select new com.study.exquerydsl.dto.MemberDto(m.username, m.age) " +
                "from Member m", MemberDto.class)
                .getResultList();

        for (MemberDto memberDto : resultList) {
            System.out.println("memberDto = " + memberDto);
        }
    }

    // Querydsl 스타일
    /*
    3가지 방식 지원
    1. 프로퍼티 접근
    2. 필드 직접 접근
    3. 생성자 사용
     */

    // 1. 프로퍼티 접근
    @Test
    public void findDtoBySetter() throws Exception {
        List<MemberDto> result = queryFactory
                .select(Projections.bean(MemberDto.class,
                        member.username,
                        member.age))
                .from(member)
                .fetch();

        for (MemberDto memberDto : result) {
            // MemberDto.class에 디폴트 생성자가 필수로 필요하다.
            // Querydsl이 기본생성자를 만들어서 set시키기 때문
            System.out.println("memberDto = " + memberDto);
        }
    }

    // 2. 필드 직접 접근
    // 얘는 getter, setter가 필요없다. DTO 필드에 값을 바로 넣는다.
    @Test
    public void findDtoByField() throws Exception {
        List<MemberDto> result = queryFactory
                .select(Projections.fields(MemberDto.class,
                        member.username,
                        member.age))
                .from(member)
                .fetch();

        for (MemberDto memberDto : result) {
            System.out.println("memberDto = " + memberDto);
        }
    }

    // 3. 생성자 접근
    @Test
    public void findDtoByConstructor() throws Exception {
        List<MemberDto> result = queryFactory
                .select(Projections.constructor(MemberDto.class,
                        member.username,
                        member.age))
                .from(member)
                .fetch();

        for (MemberDto memberDto : result) {
            System.out.println("memberDto = " + memberDto);
        }
    }

    // DTO/필드명이 엔티티와 일치하지 않는 경우, as() 사용
    @Test
    public void findUserDtoByField() throws Exception {
        QMember memberSub = new QMember("memberSub");
        List<UserDto> result = queryFactory
                .select(Projections.fields(UserDto.class,
                        member.username.as("name"),

                        // 서브쿼리는 ExpressionUtils 방법 뿐
                        ExpressionUtils.as(JPAExpressions
                                .select(memberSub.age.max())
                                .from(memberSub), "age")
                        )
                )
                .from(member)
                .fetch();

        for (UserDto userDto : result) {
            System.out.println("userDto = " + userDto);
        }
    }

    // DTO/필드명이 엔티티와 일치하지 않는 경우, 타입과 생성자만 맞춰주면 된다.
    @Test
    public void findUserDtoByConstructor() throws Exception {
        List<UserDto> result = queryFactory
                .select(Projections.constructor(UserDto.class,
                        member.username,
                        member.age))
                .from(member)
                .fetch();

        for (UserDto userDto : result) {
            System.out.println("userDto = " + userDto);
        }
    }

    /* ==== @QueryProjection ==== */
    /*
    이용 방법)
    - Dto class에서 @QueryProjection 후 Q파일 생성

    생성자 접근 방식과 차이점)
    - select에서 없는 값을 요청하면 컴파일 후에 오류를 발견할 수 있다. (Runtime error)

    제일 깔끔한 방법이지만 단점이 있다)
    - Q파일 생성한다는 점
    - DTO는 Querydsl에 의존성이 없었는데 @QueryProjection로 인해 의존성이 생긴다.
    - DTO는 여러 layer에서 사용하여 Querydsl에 의존성이 생긴다.

     */
    @Test
    public void findDtoByQueryProjection() throws Exception {
        // Ctrl+p: 하면 생성자의 타입을 볼 수 있다.
        List<MemberDto> result = queryFactory
                .select(new QMemberDto(member.username, member.age))
                .from(member)
                .fetch();

        for (MemberDto memberDto : result) {
            System.out.println("memberDto = " + memberDto);
        }
    }

    // ==== 동적쿼리 ====
    /*
    동적쿼리를 해결하는 2가지 방법
    방법1. BooleanBuilder
    방법2. Where 다중 파라미터 사용
     */

    // 방법1) BooleanBuilder
    @Test
    public void dynamicQuery_BooleanBuilder() throws Exception {
        String usernameParam = "member1";
        Integer ageParam = 10;

        List<Member> result = searchMember1(usernameParam, ageParam);
        assertThat(result.size()).isEqualTo(1);
    }

    private List<Member> searchMember1(String usernameCond, Integer ageCond) {
        // null이냐 아니냐에 따라 쿼리가 바껴야 한다.
        BooleanBuilder builder = new BooleanBuilder();

        // usernameCond이 null이 아니라는 가정 하, 초기값을 설정할 수 있다.
        // BooleanBuilder builder = new BooleanBuilder(member.username.eq(usernameCond));

        if (usernameCond != null) {
            builder.and(member.username.eq(usernameCond));
        }
        if (ageCond != null) {
            builder.and(member.age.eq(ageCond));
        }

        return queryFactory
                .selectFrom(member)
                .where(builder) // builder.and || builder.or 가능
                .fetch();
    }

    // 방법2) Where 다중 파라미터 사용, 가독성 + 재사용성! 가장 좋은 방식
    @Test
    public void dynamicQuery_WhereParam() throws Exception {
        String usernameParam = "member1";
        Integer ageParam = 10;

        List<Member> result = searchMember2(usernameParam, ageParam);
        assertThat(result.size()).isEqualTo(1);
    }

    private List<Member> searchMember2(String usernameCond, Integer ageCond) {
        return queryFactory
                .selectFrom(member)
//                .where(usernameEq(usernameCond), ageEq(ageCond))
                .where(allEq(usernameCond, ageCond))
                .fetch();
    }

    private BooleanExpression usernameEq(String usernameCond) {
        if (usernameCond == null) {
            return null; // where에 null이 되면 조건이 무시된다.
        } else {
            return member.username.eq(usernameCond);
        }
    }

    private BooleanExpression ageEq(Integer ageCond) {
        return ageCond != null ? member.age.eq(ageCond) : null;
    }

    // 메서드(자바코드)로 빠져서 다음과 같이 조립을 할 수 있다.
    private BooleanExpression allEq(String usernameCond, Integer ageCond) {
        return usernameEq(usernameCond).and(ageEq(ageCond));
    }

    // ==== 수정, 삭제 배치 쿼리 ====
    /*
    쿼리 한번으로 대량의 데이터를 수정할 때 사용

    JPA는 Entity를 가져와서 Entity 값을 바꾸면 Transaction이 commit 할 때
    flush가 일어나면서 Dirty Checking(변경감지)해서 Update 쿼리가 나간다.

    이 변경 감지는 개별 건당 쿼리를 날린다.

    한번에 쿼리를 처리하면 성능이 매우 좋아질 수 있다. -> 벌크 연산
     */
    @Test
    // @Commit // @Transaction은 테스트 후 Rollback을 시켜 원래의 데이터 복원
    public void bulkUpdate() throws Exception {
        long count = queryFactory
                .update(member)
                .set(member.username, "비회원")
                .where(member.age.lt(28))
                .execute(); // count: 영향 받은 데이터 개수

        assertThat(count).isEqualTo(2);

        /*
        em.flush();
        em.clear();
        */

        /*
        벌크 연산의 주의점)
        영속성 컨텍스트에 데이터(member1, .., member4)들이 올라가있는 상태,
        벌크 연산은 영속성 컨텍스트(1차 캐시)를 무시하고 DB에 바로 적용

        -> 영속성 컨텍스트와 DB의 데이터가 달라진다.
         */

        List<Member> result = queryFactory
                .select(member)
                .fetch();

        /*
        를 하면, DB에서 데이터를 불러와 영속성 컨텍스트에 올려야하는데 이미 데이터가 존재해서
        영속성 컨텍스트가 우선이라 DB에서 불러온 값을 무시한다.

        이런걸 repeatable read라고 한다.
         */

        for (Member member1 : result) {
            System.out.println("member1 = " + member1);
        }

        /*
        이 문제를 해결하기 위해 영속성 컨텍스트를 초기화시켜준다.
        em.flush();
        em.clear();
         */
    }

    // 모든 데이터에 1더하기, 빼고싶을때는 -1, 곱할때는 multiply()
    @Test
    public void bulkAdd() throws Exception {
        long execute = queryFactory
                .update(member)
                .set(member.age, member.age.add(1))
                .execute();
    }

    @Test
    public void bulkDelete() throws Exception {
        long execute = queryFactory
                .delete(member)
                .where(member.age.gt(18))
                .execute();
    }

    // SQL function 호출하기
    // JPA와 같이 Dialect에 등록된 내용만 호출할 수 있다.
    @Test
    public void sqlFunction() throws Exception {
        List<String> fetch = queryFactory
                .select(
                        Expressions.stringTemplate(
                                "function('replace', {0}, {1}, {2})",
                                member.username,
                                "member", // member라는 이름을
                                "M") // M으로 바꾸기
                )
                .from(member)
                .fetch();

        for (String s : fetch) {
            System.out.println("s = " + s);
        }
    }

    @Test
    public void sqlFunction2() throws Exception{
        List<String> fetch = queryFactory
                .select(member.username)
                .from(member)
                .where(member.username.eq(
                        Expressions.stringTemplate(
                                "function('lower', {0})",
                                member.username)
                        )
                )
                .fetch();

        // 이처럼 간단하고 일반화된 함수는 내장되어 있다.

        List<String> fetch2 = queryFactory
                .select(member.username)
                .from(member)
                .where(member.username.eq(member.username.lower()))
                .fetch();

        for (String s : fetch ) {
            System.out.println("s = " + s);
        }
    }
}
