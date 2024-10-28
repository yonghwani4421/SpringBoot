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
public class IfEx {
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


            Member member = new Member();
            member.setUsername("관리자");
            member.setAge(10);
            member.chnageTeam(team);

            em.persist(member);

            em.flush();
            em.clear();



//            String query = "select " +
//                                "case when m.age <= 10 then '학생요금'" +
//                                "     when m.age >= 60 then '경로요금'" +
//                                "     else '일반요금' " +
//                                "end " +
//                    "from Member m";

//            String query = "select coalesce(m.username,'이름 없는 회원') as username " +
//                    "from Member m";

            String query = "select NULLIF(m.username,'관리자') as  " +
                    "from Member m";
            List<String> resultList = em.createQuery(query, String.class).getResultList();

            for (String s : resultList) {
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
