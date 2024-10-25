package hellojpa;

import jakarta.persistence.*;
import org.hibernate.Hibernate;

import java.util.List;

/**
 * @AUTHOR dyd71
 * @DATE 2024-10-23
 * @PARAM
 * @DESCRIBE 객체 생성 & 등록
 * @VERSION 1.0
 */
public class Ex1Main {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        emf.getPersistenceUnitUtil();
        EntityManager em = emf.createEntityManager();
        //code

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try{


            Member member1 = new Member();

            member1.setUsername("Member1");

            em.persist( member1);

            Member member2 = new Member();

            member2.setUsername("Member2");

            em.persist( member2);

            em.flush();
            em.clear();

//            Member findMember = em.find(Member.class, member.getId());

//            Member findMember = em.getReference(Member.class,  member1.getId());
//            System.out.println("findMember = " + findMember.getClass());
//            System.out.println("findMember.id = " + findMember.getId());
//            System.out.println("findMember.username = " + findMember.getUsername());


            // 타입 비교 예제
//            Member m1 = em.find(Member.class, member1.getId());
//            Member m2 = em.find(Member.class, member2.getId());

//            System.out.println("m1 == m2 " + (m1.getClass() == m2.getClass()));
//            System.out.println("m1 == m2 " + (m1 instanceof Member));
//            System.out.println("m1 == m2 " + (m2 instanceof Member));

            // == 비교 타입은 무조건 맞춰서 반환해준다.
            // proxy를 먼저 반환하면 이후 entity 반환이여도 proxy로 나옴
//            Member findMember = em.find(Member.class, member1.getId());
//            System.out.println("findMember = " + findMember.getClass());

            Member reference = em.getReference(Member.class, member1.getId());
            System.out.println("reference = " + reference.getClass());

//            System.out.println(reference.getUsername());

            // 강제 초기화
            Hibernate.initialize(reference);

            // 초기화 여부 확인
            System.out.println("isLoaded = " + emf.getPersistenceUnitUtil().isLoaded(reference));
//            em.detach(reference);

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
