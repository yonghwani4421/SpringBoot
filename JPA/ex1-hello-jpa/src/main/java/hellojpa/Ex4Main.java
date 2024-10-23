package hellojpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

/**
 * @AUTHOR dyd71
 * @DATE 2024-10-23
 * @PARAM
 * @DESCRIBE 변경 감지
 * @VERSION 1.0
 */
public class Ex4Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        //code

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try{

            // 변경감지 jpa는 값이 변경되면, 트렌젝션이 커밋되는 시점에 update쿼리가 나간다.
            Member member = em.find(Member.class, 150L);
            member.setName("ZZZZ");
            // 업데이트
//            em.persist(member); => 없어도 내부의 스냅샷과의 비교를 통하여 commit 시점에 update 쿼리가 날라간다.
            System.out.println("==============================");


            tx.commit();


        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
