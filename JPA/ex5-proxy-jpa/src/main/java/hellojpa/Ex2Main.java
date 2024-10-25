package hellojpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.hibernate.Hibernate;

/**
 * @AUTHOR dyd71
 * @DATE 2024-10-23
 * @PARAM
 * @DESCRIBE 객체 생성 & 등록
 * @VERSION 1.0
 */
public class Ex2Main {
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
            member.setUsername("Member1");
            member.setTeam(team);
            em.persist(member);

            em.flush();
            em.clear();


            Member m = em.find(Member.class, member.getId());
            System.out.println("m = " + m.getClass());

            System.out.println("================= Team 조회 ================");

            System.out.println("t = " + m.getTeam().getName());
            System.out.println("t = " + m.getTeam().getClass());

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
