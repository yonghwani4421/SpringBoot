package jpql;

import jakarta.persistence.*;
import jpql.domain.Member;

import java.util.List;

/**
 * @AUTHOR dyd71
 * @DATE 2024-10-28
 * @PARAM
 * @VERSION 1.0
 */
public class Main {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        emf.getPersistenceUnitUtil();
        EntityManager em = emf.createEntityManager();
        //code

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try{

            Member member = new Member();
            member.setUsername("member1");
            member.setAge(10);
            em.persist(member);

            TypedQuery<Member> query1 = em.createQuery("select m from Member m", Member.class);
//            TypedQuery<Member> query2 = em.createQuery("select m.username from Member m", Member.class);
//            Query query3 = em.createQuery("select m.username, m.age from Member m");

            Member singleResult = em.createQuery("select m from Member m where m.username = :username ", Member.class)
                    .setParameter("username", "member1")
                    .getSingleResult();


            System.out.println("singleResult = " + singleResult.getUsername());

            List<Member> resultList =
                    query1.getResultList();

            for (Member member1 : resultList) {
                System.out.println("member1 = " + member1);
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
