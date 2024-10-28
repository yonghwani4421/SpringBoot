package hellojpa;

import jakarta.persistence.*;

import java.util.List;
import java.util.Set;

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

//            Address adress = new Address("city", "street", "10000");
//
//            Member member1 = new Member();
//            member1.setUsername("member1");
//            member1.setHomeAdress(adress);
//            member1.setWorkPeriod(new Period());


//            Address copyAdress = new Address(adress.getCity(), adress.getStreet(), adress.getZipcode());

//            Member member2 = new Member();
//            member2.setUsername("member2");
//            member2.setHomeAdress(copyAdress);
//            member2.setWorkPeriod(new Period());

//            em.persist(member1);
//            em.persist(member2);

            // 불변객체로 설계하여야 한다.
//            member1.getHomeAdress().setCity("newCity");


//            Address newAdress = new Address("NewCity", adress.getStreet(), adress.getZipcode());

//            member1.setHomeAdress(newAdress);



            Address adress = new Address("homeCity", "street", "10000");

            Member member1 = new Member();
            member1.setUsername("member1");
            member1.setHomeAdress(adress);

            member1.getFavoriteFoods().add("치킨");
            member1.getFavoriteFoods().add("족발");
            member1.getFavoriteFoods().add("피자");

            member1.getAddressHistory().add(new AddressEntity("old1","street","10000"));
            member1.getAddressHistory().add(new AddressEntity("old2","street","10000"));

            em.persist(member1);


            em.flush();
            em.clear();


            System.out.println("==============Start===================");
            Member findMember = em.find(Member.class, member1.getId());


            // 조회
//            List<Address> addressHistory = findMember.getAddressHistory();
//
//            for (Address address : addressHistory) {
//                System.out.println("address.city = " + address.getCity());
//            }
//
//            // 지연 로딩
//            Set<String> favoriteFoods = findMember.getFavoriteFoods();
//            for (String favoriteFood : favoriteFoods) {
//                System.out.println("favoriteFood = " + favoriteFood);
//            }
            // 수정 homecity => newcity  객체를 새로 생성해서 교체해줘야함
//            Address a = findMember.getHomeAdress();
//            findMember.setHomeAdress(new Address("newCity", a.getStreet(), a.getZipcode()));

            // 치킨 ==> 한식
//            findMember.getFavoriteFoods().remove("치킨");
//            findMember.getFavoriteFoods().add("한식");

            //
//            findMember.getAddressHistory().remove(new Address("old1","street","10000"));
//            findMember.getAddressHistory().add(new Address("newCity1","street","10000"));

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
