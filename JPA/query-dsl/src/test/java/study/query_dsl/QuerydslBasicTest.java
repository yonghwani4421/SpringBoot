package study.query_dsl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.query_dsl.dto.MemberDto;
import study.query_dsl.dto.QMemberDto;
import study.query_dsl.dto.UserDto;
import study.query_dsl.entity.Member;
import study.query_dsl.entity.QMember;
import study.query_dsl.entity.QTeam;
import study.query_dsl.entity.Team;

import java.util.List;

import static com.querydsl.jpa.JPAExpressions.*;
import static org.assertj.core.api.Assertions.*;
import static study.query_dsl.entity.QMember.member;
import static study.query_dsl.entity.QTeam.*;

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
    @DisplayName("JPQL 작성")
    public void startJPQL() throws Exception{
        // given
        Member findByJPQL = em.createQuery("select m from Member m where m.username = :username", Member.class).setParameter("username", "member1")
                .getSingleResult();

        // then
        assertThat(findByJPQL.getUsername()).isEqualTo("member1");
    }

    @Test
    @DisplayName("query dsl 작성")
    public void startQuerydsl() throws Exception{
        // given
//        QMember m = new QMember("m");

//        QMember m = QMember.member;

        // 스태틱 임포트 기능으로 깔끔하게 사용가능
        Member findMember = queryFactory
                .select(member)
                .from(member)
                .where(member.username.eq("member1"))
                .fetchOne();
        // then
        assertThat(findMember.getUsername()).isEqualTo("member1");
    }


    @Test
    @DisplayName("member조건 조회")
    public void search() throws Exception{
        // given
        Member findMember = queryFactory
                .selectFrom(member)
                .where(member.username.eq("member1")
                        .and(member.age.eq(10)))
                .fetchOne();

        // when

        // then
        assertThat(findMember.getUsername()).isEqualTo("member1");


    }


    @Test
    @DisplayName("member조건 조회")
    public void searchAndParam() throws Exception{
        // given
        Member findMember = queryFactory
                .selectFrom(member)
                .where(
                        member.username.eq("member1")
                        ,member.age.eq(10)
                )
                .fetchOne();

        // when

        // then
        assertThat(findMember.getUsername()).isEqualTo("member1");


    }

    @Test
    @DisplayName("기본 조회")
    public void resultFetch() throws Exception{
        // given
//        List<Member> fetch = queryFactory.selectFrom(member)
//                .fetch();
//
//        Member fetchOne = queryFactory.selectFrom(member)
//                .fetchOne();
//
//
//        Member fetchFirst = queryFactory.selectFrom(member)
//                .fetchFirst();

//        QueryResults<Member> results = queryFactory
//                .selectFrom(member)
//                .fetchResults();
//
//        results.getResults();
//        List<Member> content = results.getResults();

        long total = queryFactory.selectFrom(member)
                .fetchCount();

    }

    /**
     * 회원 정렬 순서
     * 1. 회원 나이 내림차순(desc)
     * 2. 회원 이름 올림차순(asc)
     * 단, 2에서 회원 이름이 없으면 마지막에 출력(nulls last)
     * @throws Exception
     */
    @Test
    @DisplayName("정렬")
    public void sort() throws Exception{


//        select m1_0.member_id,m1_0.age,m1_0.team_id,m1_0.username
//        from member m1_0
//        where m1_0.age=100
//        order by m1_0.age desc,m1_0.username asc nulls last;
        // given

        em.persist(new Member(null, 100));
        em.persist(new Member("member5", 100));
        em.persist(new Member("member6", 100));


        List<Member> result = queryFactory.selectFrom(member)
                .where(member.age.eq(100))
                .orderBy(member.age.desc(), member.username.asc().nullsLast())
                .fetch();

        // when
        Member member5 = result.get(0);
        Member member6 = result.get(1);
        Member memberNull = result.get(2);

        // then
        assertThat(member5.getUsername()).isEqualTo("member5");
        assertThat(member6.getUsername()).isEqualTo("member6");
        assertThat(memberNull.getUsername()).isNull();
    }

    @Test
    @DisplayName("페이징")
    public void paging1() throws Exception{
        // given

        List<Member> result = queryFactory
                .selectFrom(member)
                .orderBy(member.username.desc())
                .offset(1)
                .limit(2)
                .fetch();
        // when

        assertThat(result.size()).isEqualTo(2);
        // then
    }

    @Test
    @DisplayName("페이징")
    public void paging2() throws Exception{
        // given

        QueryResults<Member> results = queryFactory
                .selectFrom(member)
                .orderBy(member.username.desc())
                .offset(1)
                .limit(2)
                .fetchResults();
        // when

        assertThat(results.getTotal()).isEqualTo(4);
        assertThat(results.getLimit()).isEqualTo(2);
        assertThat(results.getOffset()).isEqualTo(1);
        assertThat(results.getResults().size()).isEqualTo(2);
        // then
    }

    @Test
    @DisplayName("집합")
    public void aggregation() throws Exception{
        // given

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

        Tuple tuple = result.get(0);
        assertThat(tuple.get(member.count())).isEqualTo(4);
        assertThat(tuple.get(member.age.sum())).isEqualTo(100);
        assertThat(tuple.get(member.age.avg())).isEqualTo(25);
        assertThat(tuple.get(member.age.max())).isEqualTo(40);
        assertThat(tuple.get(member.age.min())).isEqualTo(10);

    }

    /**
     * 팀의 이름과 각 팀의 평균 연령을 구해라
     * @throws Exception
     */
    @Test
    @DisplayName("group by")
    public void group() throws Exception{
        // given
        List<Tuple> result = queryFactory
                .select(team.name, member.age.avg())
                .from(member)
                .join(member.team, team)
                .groupBy(team.name)
                .fetch();

        Tuple teamA = result.get(0);
        Tuple teamB = result.get(1);

        assertThat(teamA.get(team.name)).isEqualTo("teamA");
        assertThat(teamA.get(member.age.avg())).isEqualTo(15);
        assertThat(teamB.get(team.name)).isEqualTo("teamB");
        assertThat(teamB.get(member.age.avg())).isEqualTo(35);

    }

    /**
     * 팀 A에 소속된 모든 회원
     * @throws Exception
     */
    @Test
    @DisplayName("join")
    public void join() throws Exception{
        // given
        List<Member> result = queryFactory
                .selectFrom(member)
                .join(member.team, team)
                .where(team.name.eq("teamA"))
                .fetch();


        assertThat(result)
                .extracting("username")
                .containsExactly("member1","member2");
    }

    /**
     * 세타 조인
     * 회원의 이름이 팀 이름과 같은 회원 조회
     * @throws Exception
     */
    @Test
    @DisplayName("theta join")
    public void theta_join() throws Exception{
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
                .containsExactly("teamA","teamB");
    }

    /**
     *  예시 ) 회원과 팀을 조인하면서, 팀 이름이 teamA인 팀만 조인, 회원은 모두 조인
     *  JPQL: selct m, t from Member m left join m.team t on t.name = 'teamA'
     * @throws Exception
     */

    @Test
    @DisplayName("")
    public void QuerydslBasicTest() throws Exception{
        // given

        List<Tuple> result = queryFactory.select(member, team)
                .from(member)
                .leftJoin(member.team, team)
                .on(team.name.eq("teamA"))
                .fetch();

        for (Tuple tuple : result) {
            System.out.println("tuple = " + tuple);
        }

    }

    /**
     * 연관관계가 없는 엔티티 외부 조인
     * 회원의 이름이 팀 이름과 같은 대상 외부 조인
     * @throws Exception
     */
    @Test
    @DisplayName("join_on_no_relation")
    public void join_on_no_relation() throws Exception{
        em.persist(new Member("teamA"));
        em.persist(new Member("teamB"));
        em.persist(new Member("teamC"));

        List<Tuple> result = queryFactory
                .select(member,team)
                .from(member)
                .leftJoin(team).on(member.username.eq(team.name))
                .fetch();


        for (Tuple tuple : result) {
            System.out.println("tuple = " + tuple);
        }
    }

    @PersistenceUnit
    EntityManagerFactory emf;
    @Test
    @DisplayName("fetchJoinNo")
    public void fetchJoinNo() throws Exception{
        em.flush();
        em.clear();

        Member findMember = queryFactory
                .selectFrom(member)
                .where(member.username.eq("member1"))
                .fetchOne();

        // when

        boolean loaded = emf.getPersistenceUnitUtil().isLoaded(findMember.getTeam());


        // then
        assertThat(loaded).as("페치조인 미적용").isFalse();


    }

    @Test
    @DisplayName("fetchJoinUse")
    public void fetchJoinUse() throws Exception{
        em.flush();
        em.clear();

        Member findMember = queryFactory
                .selectFrom(member)
                .join(member.team, team).fetchJoin()
                .where(member.username.eq("member1"))
                .fetchOne();

        // when

        // 미리 로드를 해오는지 확인
        boolean loaded = emf.getPersistenceUnitUtil().isLoaded(findMember.getTeam());

        // then
        assertThat(loaded).as("페치조인 미적용").isTrue();
    }

    /**
     * 나이가 가장 많은 회원 조회
     * @throws Exception
     */
    @Test
    @DisplayName("subQuery")
    public void subQuery() throws Exception{

        QMember memberSub = new QMember("memberSub");
        // given
        List<Member> result = queryFactory
                .selectFrom(member)
                .where(member.age.eq(
                        select(memberSub.age.max())
                                .from(memberSub)
                ))
                .fetch();
        // when


        // then
        assertThat(result).extracting("age")
                .containsExactly(40);
    }

    /**
     * 나이가 평균 이상인 회원
     * @throws Exception
     */
    @Test
    @DisplayName("subQueryGoe")
    public void subQueryGoe() throws Exception{

        QMember memberSub = new QMember("memberSub");
        // given
        List<Member> result = queryFactory
                .selectFrom(member)
                .where(member.age.goe(
                        select(memberSub.age.avg())
                                .from(memberSub)
                ))
                .fetch();
        // when


        // then
        assertThat(result).extracting("age")
                .containsExactly(30,40);
    }

    /**
     * 나이가 평균 이상인 회원
     * @throws Exception
     */
    @Test
    @DisplayName("subQueryIn")
    public void subQueryIn() throws Exception{

        QMember memberSub = new QMember("memberSub");
        // given
        List<Member> result = queryFactory
                .selectFrom(member)
                .where(member.age.in(
                        select(memberSub.age)
                                .from(memberSub)
                                .where(memberSub.age.gt(10))
                ))
                .fetch();
        // when


        // then
        assertThat(result).extracting("age")
                .containsExactly(20,30,40);
    }

    @Test
    @DisplayName("selectSubQuery")
    public void selectSubQuery() throws Exception{

        QMember memberSub = new QMember("memberSub");
        // given
        List<Tuple> result = queryFactory
                .select(member.username,
                        select(memberSub.age.avg())
                                .from(memberSub))
                .from(member)
                .fetch();

        for (Tuple tuple : result) {
            System.out.println("tuple = " + tuple);
        }
    }

    @Test
    @DisplayName("basicCase")
    public void basicCase() throws Exception{
        // given

        List<String> reulst = queryFactory
                .select(member.age
                        .when(10).then("열살")
                        .when(20).then("스무살")
                        .otherwise("기타"))
                .from(member)
                .fetch();

        for (String s : reulst) {
            System.out.println("s = " + s);
        }
    }

    @Test
    @DisplayName("complexCase")
    public void complexCase() throws Exception{
        // given
        List<String> result = queryFactory
                .select(new CaseBuilder()
                        .when(member.age.between(0, 20)).then("0~20살")
                        .when(member.age.between(21, 30)).then("21~30살")
                        .otherwise("기타"))
                .from(member)
                .fetch();

        // when

        for (String s : result) {
            System.out.println("s = " + s);
        }

        // then
    }

    @Test
    @DisplayName("constant")
    public void constant() throws Exception{
        // given
        List<Tuple> result = queryFactory.select(member.username, Expressions.constant("A"))
                .from(member)
                .fetch();
        // when

        for (Tuple tuple : result) {
            System.out.println("tuple = " + tuple);
        }

        // then
    }

    @Test
    @DisplayName("concat")
    public void concat() throws Exception{
        // given
        // {username}_{age}
        List<String> result = queryFactory.select(member.username.concat("_").concat(member.age.stringValue()))
                .from(member)
                .where(member.username.eq("member1"))
                .fetch();
        // when
        for (String s : result) {
            System.out.println("s = " + s);
        }
    }

    @Test
    @DisplayName("simpleProjection")
    public void simpleProjection() throws Exception{
        // given
        List<String> result = queryFactory
                .select(member.username)
                .from(member)
                .fetch();
        // when

        for (String s : result) {
            System.out.println("s = " + s);
        }

        // then
    }
    
    
    @Test
    @DisplayName("tupleProjection")
    public void tupleProjection() throws Exception{
        // given
        List<Tuple> result = queryFactory
                .select(member.username, member.age)
                .from(member)
                .fetch();

        // when
        for (Tuple tuple : result) {
            String username = tuple.get(member.username);
            Integer age = tuple.get(member.age);

            System.out.println("username = " + username);
            System.out.println("age = " + age);
        }
        
        // then
    }

    @Test
    @DisplayName("findDtoByJPQL")
    public void findDtoByJPQL() throws Exception{
        // given
        // new 명령어를 사용하여 package이름을 적어줘야함
        List<MemberDto> result = em.createQuery("select new study.query_dsl.dto.MemberDto(m.username, m.age) from Member m", MemberDto.class)
                .getResultList();

        // when
        for (MemberDto memberDto : result) {
            System.out.println("memberDto = " + memberDto);
        }
        // then
    }

    @Test
    @DisplayName("findDtoBySetter")
    public void findDtoBySetter() throws Exception{
        // given
        // setter를 통해서 값이 들어가는 방법
        List<MemberDto> result = queryFactory
                .select(Projections.bean(MemberDto.class, member.username, member.age))
                .from(member)
                .fetch();
        // when
        for (MemberDto memberDto : result) {
            System.out.println("memberDto = " + memberDto);
        }

        // then
    }

    @Test
    @DisplayName("findDtoByField")
    public void findDtoByField() throws Exception{
        // given
        // setter 필요없이
        List<MemberDto> result = queryFactory
                .select(Projections.fields(MemberDto.class, member.username, member.age))
                .from(member)
                .fetch();
        // when
        for (MemberDto memberDto : result) {
            System.out.println("memberDto = " + memberDto);
        }

        // then
    }

    @Test
    @DisplayName("findDtoByConstructor")
    public void findDtoByConstructor() throws Exception{
        // given
        // 생성자 호출 기반
        List<UserDto> result = queryFactory
                .select(Projections.constructor(UserDto.class
                        , member.username
                        , member.age))
                .from(member)
                .fetch();
        // when
        for (UserDto memberDto : result) {
            System.out.println("memberDto = " + memberDto);
        }

        // then
    }

    @Test
    @DisplayName("findUserDto")
    public void findUserDto() throws Exception{
        // given
        QMember memberSub = new QMember("memberSub");
        // 필드 기반에서 필드 이름이 다른경우 다른이름으로 지정해줘야함
        List<UserDto> result = queryFactory
                .select(Projections.fields(UserDto.class
                        , member.username.as("name")
                        , ExpressionUtils.as(JPAExpressions
                                .select(memberSub.age.max())
                                .from(memberSub),"age")
                ))
                .from(member)
                .fetch();
        // when
        for (UserDto userDto : result) {
            System.out.println("userDto = " + userDto);
        }

        // then
    }


    @Test
    @DisplayName("findDtoByQueryProjection")
    public void findDtoByQueryProjection() throws Exception{
        // given
        List<MemberDto> result = queryFactory
                .select(new QMemberDto(member.username, member.age))
                .from(member)
                .fetch();
        // when

        for (MemberDto memberDto : result) {
            System.out.println("memberDto = " + memberDto);
        }

        // then
    }

    @Test
    @DisplayName("dynamicQuery_BooleanBuilder")
    public void dynamicQuery_BooleanBuilder() throws Exception{
        // given
        String usernameParam = "member1";
        Integer ageParam = null;

        List<Member> result = searchMember1(usernameParam, ageParam);
        assertThat(result.size()).isEqualTo(1);
        // when

        // then
    }

    private List<Member> searchMember1(String usernameCond, Integer ageCond) {
        BooleanBuilder builder = new BooleanBuilder();
        if (usernameCond != null){
            builder.and(member.username.eq(usernameCond));
        }
        if (ageCond != null) {
            builder.and(member.age.eq(ageCond));
        }
        return queryFactory
                .selectFrom(member)
                .where(builder)
                .fetch();
    }

    @Test
    @DisplayName("dynamicQuery_WhereParam")
    public void dynamicQuery_WhereParam() throws Exception{
        // given
        String usernameParam = "member1";
        Integer ageParam = null;

        List<Member> result = searchMember2(usernameParam, ageParam);
        // when

        // then
        assertThat(result.size()).isEqualTo(1);
    }

    private List<Member> searchMember2(String usernameCond, Integer ageCond) {
        return queryFactory
                .selectFrom(member)
//                .where(usernameEq(usernameCond), ageEq(ageCond))
                .where(allEq(usernameCond,ageCond))
                .fetch();
    }

    private BooleanExpression ageEq(Integer ageCond) {
        return ageCond != null ? member.age.eq(ageCond) : null;
    }

    private BooleanExpression usernameEq(String usernameCond) {
        return usernameCond != null ? member.username.eq(usernameCond) : null;
    }
    private BooleanExpression allEq(String usernameCond, Integer ageCond){
        return usernameEq(usernameCond).and(ageEq(ageCond));
    }

    @Test
    @DisplayName("updateBulk")
    public void updateBulk() throws Exception{
        // given
        long count = queryFactory
                .update(member)
                .set(member.username, "비회원")
                .where(member.age.lt(28))
                .execute();
        // when
        assertThat(count).isEqualTo(2);
        // then
    }

    @Test
    @DisplayName("updateBulkAdd")
    public void updateBulkAdd() throws Exception{
        // given
        long count = queryFactory
                .update(member)
                .set(member.age, member.age.add(1))
                .execute();
        // when

        em.flush();
        em.clear();
        List<Member> result = queryFactory
                .selectFrom(member).fetch();
        // then
        for (Member member1 : result) {
            System.out.println("member1.age = " + member1.getAge());
        }
    }

    @Test
    @DisplayName("updateBulkMultiply")
    public void updateBulkMultiply() throws Exception{
        // given
        long count = queryFactory
                .update(member)
                .set(member.age, member.age.multiply(2))
                .execute();
        // when

        em.flush();
        em.clear();
        List<Member> result = queryFactory
                .selectFrom(member).fetch();
        // then
        for (Member member1 : result) {
            System.out.println("member1.age = " + member1.getAge());
        }
    }
    @Test
    @DisplayName("deleteBulk")
    public void deleteBulk() throws Exception{
        // given
        long count = queryFactory
                .delete(member)
                        .where(member.age.gt(18))
                                .execute();
        // when
        em.flush();
        em.clear();
        List<Member> result = queryFactory
                .selectFrom(member).fetch();
        // then
        for (Member member1 : result) {
            System.out.println("member1.age = " + member1.getAge());
        }
    }

    @Test
    @DisplayName("sqlFunction")
    public void sqlFunction() throws Exception{
        // given
        List<String> result = queryFactory
                .select(Expressions.stringTemplate(
                        "function('replace', {0},{1},{2})"
                        , member.username, "member", "M"))
                .from(member)
                .fetch();
        // when
        for (String s : result) {
            System.out.println("s = " + s);
        }
    }

    @Test
    @DisplayName("sqlFunction2")
    public void sqlFunction2() throws Exception{
        // given
        List<String> result = queryFactory
                .select(member.username)
                .from(member)
//                .where(member.username.eq(
//                        Expressions.stringTemplate("function('lower',{0})", member.username))
//                )
                .where(member.username.eq(member.username.lower()))
                .fetch();

        // when
        for (String s : result) {
            System.out.println("s = " + s);
        }
        // then
    }

}
