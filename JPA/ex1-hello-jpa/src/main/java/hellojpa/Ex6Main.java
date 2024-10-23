package hellojpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

/**
 * @AUTHOR dyd71
 * @DATE 2024-10-23
 * @PARAM
 * @DESCRIBE 준영속 상태로 만들기
 * @VERSION 1.0
 */
public class Ex6Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        //code

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try{
            Member member = em.find(Member.class, 150L);

            member.setName("AAAA");

            // 특정 엔티티만 준영속 상태
//            em.detach(member);
            // 영속성 컨텍스트 전체 초기화
            em.clear();

            Member member1 = em.find(Member.class, 150L);


            System.out.println("========================");

            // 쿼리가 날라가지 않음
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
