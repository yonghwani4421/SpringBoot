package jpql;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jpql.domain.Member;
import jpql.domain.Team;

import java.util.List;

/**
 * @AUTHOR dyd71
 * @DATE 2024-10-28
 * @PARAM
 * @VERSION 1.0
 */
public class BasicFuntionEx {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        emf.getPersistenceUnitUtil();
        EntityManager em = emf.createEntityManager();
        //code

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try{


            Team team = new Team();
            team.setName("teamA");
            em.persist(team);


            Member member1 = new Member();
            member1.setUsername("관리자1");
            member1.setAge(10);
            member1.chnageTeam(team);
            Member member2 = new Member();
            member2.setUsername("관리자2");
            member2.setAge(10);
            member2.chnageTeam(team);

            em.persist(member1);
            em.persist(member2);

            em.flush();
            em.clear();



//            String query = "select 'a' || 'b' from Member m";
//            String query = "select concat('a','b') from Member m";
//            String query = "select locate('de','abcdefg') from Member m";
            String query = "select size(t.members) from Team t";
            List<Integer> resultList = em.createQuery(query, Integer.class).getResultList();

            for (Integer s : resultList) {
                System.out.println("s = " + s);
            }


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
