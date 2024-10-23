package hellojpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

/**
 * @AUTHOR dyd71
 * @DATE 2024-10-23
 * @PARAM
 * @DESCRIBE 1차 캐시 동일성 보장
 * @VERSION 1.0
 */
public class Ex2Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        //code

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try{
            // 1차 캐시 확인
            Member findMember = em.find(Member.class, 100L);


            System.out.println("findMember = " + findMember.getId());
            System.out.println("findMember = " + findMember.getName());

//             엔티티 동일성 보장
            Member member1 = em.find(Member.class, 100L);
            Member member2 = em.find(Member.class, 100L);

            System.out.println(member1 == member2);


            tx.commit();


        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
