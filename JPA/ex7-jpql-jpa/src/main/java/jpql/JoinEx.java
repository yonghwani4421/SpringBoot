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
public class JoinEx {
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
            member.setUsername("member1");
            member.setAge(10);
            member.chnageTeam(team);

            em.persist(member);

            em.flush();
            em.clear();



//            String query = "select m from Member m inner join m.team t";
//            String query = "select m from Member m left join m.team t";
//            String query = "select m from Member m, Team t where m.username = t.name ";
            String query = "select m from Member m left join Team t on m.username = t.name ";
            List<Member> resultList = em.createQuery(query, Member.class).getResultList();



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
