package hellojpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

/**
 * @AUTHOR dyd71
 * @DATE 2024-10-23
 * @PARAM
 * @DESCRIBE 쓰기 지연
 * @VERSION 1.0
 */
public class Ex3Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        //code

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try{

            // 쓰기 지연
            Member member1 = new Member(170L, "A");
            Member member2 = new Member(180L, "B");

            em.persist(member1);
            em.persist(member2);

            System.out.println("===================================");


            // commit  시점에 한번에 쿼리가 나간다.

            tx.commit();


        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
