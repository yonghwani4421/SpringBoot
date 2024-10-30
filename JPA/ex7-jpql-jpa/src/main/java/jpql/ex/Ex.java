package jpql.ex;

import jakarta.persistence.*;
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
public class Ex {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        emf.getPersistenceUnitUtil();
        EntityManager em = emf.createEntityManager();
        //code

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try{


            Team team = new Team();
            em.persist(team);

            Member member1 = new Member();
            member1.setUsername("관리자1");
            member1.setTeam(team);
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("관리자2");
            member2.setTeam(team);
            em.persist(member2);



            em.flush();
            em.clear();

            // 경로 표현식
            // 1. 상태 필드
            String query1 = "select m.username from Member m";

            // 2. 단일 값 연관 경로 => 뭇기적 내부 조인 발생, 탐색
            String query2 = "select m.team from Member m";

            // 3. 컬렉션 값 연관 경로
            String query3 = "select t.members from Team t";

            Collection resultList = em.createQuery(query3, Collections.class).getResultList();

            for (Object o : resultList) {
                System.out.println("o = " + o);
            }




            //
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
