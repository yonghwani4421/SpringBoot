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
public class Ex2Main {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        //code

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try{
            Team team = new Team();
            team.setName("TeamA");
//            team.getMembers().add(member);
            em.persist(team);

            Member member = new Member();
            member.setUsername("Member1");
            member.changeTeam(team);
            em.persist(member);

            // 양방향 연관관계에서는 양쪽에 값을 전부 세팅하는게 좋다.
            // 1차캐시에만 있는 상태로 아래의 코드가 없으면 list size 0
            // 아래코드를 changeTeam 안에 넣기
            // 연관관계 편의 메소드 사용
//            team.getMembers().add(member);

            em.flush();
            em.clear();

            Team findTeam = em.find(Team.class, team.getId());


            List<Member> members = findTeam.getMembers();
            for (Member member1 : members) {
                System.out.println("member = " + member1);
            }


            tx.commit();
        } catch (Exception e) {
            tx.rollback();


        } finally {
            em.close();
        }
        emf.close();

    }
}
