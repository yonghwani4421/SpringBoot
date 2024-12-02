package study.query_dsl;

import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.query_dsl.entity.Member;
import study.query_dsl.entity.QMember;
import study.query_dsl.entity.Team;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static study.query_dsl.entity.QMember.member;

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
    @DisplayName("")
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

        // when

        // then
    }


}
