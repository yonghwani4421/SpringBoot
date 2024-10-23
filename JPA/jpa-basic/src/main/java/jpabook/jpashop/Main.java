package jpabook.jpashop;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;

/**
 * @AUTHOR dyd71
 * @DATE 2024-10-23
 * @PARAM
 * @VERSION 1.0
 */
public class Main {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        //code

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try{

            // 데이터 중심의 설계 => db관점 프로그래밍
            // 객체지향적인 설계가 아님 관계형 db에 맞춰 개발하는 방향
            // 객체 그래프 탐색이 불가능함

            Order order = em.find(Order.class, 1L);
            Long memberId = order.getMemberId();

            Member member = em.find(Member.class, memberId);
            System.out.println("member = " + member);


            tx.commit();
        } catch (Exception e) {
            tx.rollback();



        } finally {
            em.close();
        }
        emf.close();

    }
}