package hellojpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.time.LocalDateTime;
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


            Movie movie = new Movie();

            movie.setDirector("aaaa");
            movie.setActor("bbb");
            movie.setName("바람과함께사라지다.");
            movie.setPrice(10000);
            movie.setCreatedDate(LocalDateTime.now());
            movie.setCreatedBy("kim");

            em.persist(movie);

            em.flush();
            em.clear();

            Movie findMovie = em.find(Movie.class, movie.getId());
            System.out.println("movie1 = " + findMovie.getName());

            tx.commit();
        } catch (Exception e) {
            tx.rollback();


        } finally {
            em.close();
        }
        emf.close();

    }
}
