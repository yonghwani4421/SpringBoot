package jpql.ex;

import jakarta.persistence.*;
import jpql.domain.Member;

import java.util.List;

/**
 * @AUTHOR dyd71
 * @DATE 2024-10-28
 * @PARAM
 * @VERSION 1.0
 */
public class ProjectionEx {
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

            em.flush();
            em.clear();

//            List<Member> resultList = em.createQuery("select m.team from Member m", Member.class).getResultList();
//            List<Team> resultList = em.createQuery("select t from Member m join m.team t", Team.class).getResultList();
//            List<MemberDTO> resultList = em.createQuery("select new jpql.MemberDTO(m.username, m.age) from Member m", MemberDTO.class)
//                    .getResultList();

//            MemberDTO memberDTO = resultList.get(0);


//            System.out.println("memberDTO = " + memberDTO.getUsername());
//            Object o = resultList.get(0);
//            Object[] result = (Object[]) o;
//
//            System.out.println("result = " + result[0]);
//            System.out.println("result = " + result[1]);

//            Member findMember = resultList.get(0);

//            findMember.setAge(20);

//            for (Team team : resultList) {
//                System.out.println(team.getName());
//            }








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
