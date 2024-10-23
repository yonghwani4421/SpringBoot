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
public class Ex1Main {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        //code

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try{

            // 회원 등록
//            Member member = new Member();
//            member.setId(2L);
//            member.setName("HelloB");
//            em.persist(member);

            // 회원 조회
//            Member findMember = em.find(Member.class, 1L);
//            System.out.println("findMember = " + findMember.getId());
//            System.out.println("findMember = " + findMember.getName());


            // 회원 삭제
//            Member findMember = em.find(Member.class,1L);
//            em.remove(findMember);

            // 회원 수정
//            Member findMember = em.find(Member.class, 2L);
//
//            findMember.setName("HelloJPA");


            // JPQL
            List<Member> result = em.createQuery("select m from Member as m", Member.class)
                    .setFirstResult(5)
                    .setMaxResults(8)
                    .getResultList();

            for (Member member : result) {
                System.out.println(member.getName());
            }

            tx.commit();




            tx.commit();
        } catch (Exception e) {
            tx.rollback();


        } finally {
            em.close();
        }
        emf.close();

    }
}
