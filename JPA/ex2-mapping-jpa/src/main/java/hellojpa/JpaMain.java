package hellojpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

/**
 * @AUTHOR dyd71
 * @DATE 2024-10-23
 * @PARAM
 * @DESCRIBE 객체 생성 & 등록
 * @VERSION 1.0
 */
public class JpaMain {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        //code

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try{

            // IDENTITY 타입에서 id 값을 db에 위임하므로 id값을 인지 할 수 없어
            // persistent시 쓰기지연 기능을 쓸 수 없음
            Member2 member = new Member2();

            member.setName("c");

            System.out.println("=========================");
            em.persist(member);
            System.out.println("member.id = " + member.getId());
            System.out.println("=========================");
            tx.commit();
        } catch (Exception e) {
            tx.rollback();


        } finally {
            em.close();
        }
        emf.close();

    }
}
