package jpql.ex;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jpql.domain.Member;
import jpql.domain.Team;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @AUTHOR dyd71
 * @DATE 2024-10-28
 * @PARAM
 * @VERSION 1.0
 */
public class FetchJoinEx {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        emf.getPersistenceUnitUtil();
        EntityManager em = emf.createEntityManager();
        //code

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try{


            Team teamA = new Team();
            teamA.setName("팀A");
            em.persist(teamA);

            Team teamB = new Team();
            teamB.setName("팀B");
            em.persist(teamB);

            Member member1 = new Member();
            member1.setUsername("회원1");
            member1.setTeam(teamA);
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("회원2");
            member2.setTeam(teamA);
            em.persist(member2);

            Member member3 = new Member();
            member3.setUsername("회원3");
            member3.setTeam(teamB);
            em.persist(member3);




            em.flush();
            em.clear();

            // 페치 조인 X
            String query1 = "select m from Member m";
            // 페치 조인 O
            String query2 = "select m from Member m join fetch m.team";




//            List<Member> resultList = em.createQuery(query2, Member.class)
//                    .getResultList();
//            for (Member member : resultList) {
//                System.out.println("meber = " + member.getUsername() +", " +member.getTeam().getName());
//            }
            // 회원1, 팀A (SQL)
            // 회원2, 팀A (1차캐시)
            // 회원3, 팀B (SQL)
            // 회원 100명 -> 최악의 경우에 100번 쿼리를 날림 ( N + 1 )
            // 해당 문제는 페치 조인으로 풀어야함


            // 컬렉션 페치 조인
//            String query3 = "select t from Team t join fetch t.members";
//
//            List<Team> resultList1 = em.createQuery(query3, Team.class)
//                    .getResultList();
//
//            for (Team team : resultList1) {
//                System.out.println("team = " + team.getName() + "| members = " + team.getMembers().size());
//                for (Member member :team.getMembers())
//                {
//                    System.out.println("username = " + member.getUsername() + ", member = " + member);
//
//                }
//            }
            // 페치 조인과 DISTINCT
            String query4 = "select distinct t from Team t join fetch t.members";

            List<Team> resultList2 = em.createQuery(query4, Team.class)
                    .getResultList();

            System.out.println("resultList2 = " + resultList2.size());

            // Named query

//            em.createQuery("Member.findByUsername", Member.class)
//                    .setParameter("username","회원1")
//                    .getResultList();
//
//            System.out.println("resultList2 = " + resultList2.size());


            int resultCnt = em.createQuery("update Member m set m.age = 20")
                    .executeUpdate();

            System.out.println("resultCnt = " + resultCnt);


            em.clear();
            Member member = em.find(Member.class, member1.getId());

            System.out.println("member = " + member.getAge());
//            System.out.println("member1.getAge = " + member1.getAge());


            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();

        } finally {
            em.close();
        }
        emf.close();

    }
}
