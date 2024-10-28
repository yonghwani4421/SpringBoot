package jpql;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jpql.domain.Member;
import jpql.domain.MemberType;
import jpql.domain.Team;

import java.util.List;
import java.util.Objects;

/**
 * @AUTHOR dyd71
 * @DATE 2024-10-28
 * @PARAM
 * @VERSION 1.0
 */
public class SubQueryEx {
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
            member.setMemberType(MemberType.ADMIN);
            member.chnageTeam(team);

            em.persist(member);

            em.flush();
            em.clear();


            String query = "select m.username, 'HELLO', true from Member m " +
                    "where m.type = :userType";
            List<Object[]> resultList = em.createQuery(query)
                    .setParameter("userType",MemberType.ADMIN)
                    .getResultList();

            System.out.println("size : " + resultList.size());

            for (Object[] objects : resultList) {
                System.out.println("objects = " + objects[0]);
                System.out.println("objects = " + objects[1]);
                System.out.println("objects = " + objects[2]);
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
