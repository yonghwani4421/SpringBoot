package hellojpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.List;

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
            Member member = new Member();

            member.setId(2L);
            member.setAge(10);
            member.setRoleType(RoleType.ADMIN);
            member.setUsername("ggggaaa");
            em.persist(member);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();


        } finally {
            em.close();
        }
        emf.close();

    }
}
