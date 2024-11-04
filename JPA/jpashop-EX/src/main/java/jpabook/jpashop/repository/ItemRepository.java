package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {
    private final EntityManager em;

    public void save(Item item){
        // 신규등록
        if (item.getId() == null){
            em.persist(item);
        } else { // 기존에 있는 item
            em.merge(item);
        }
    }

    public Item findOne(Long id){
        return em.find(Item.class, id);
    }
    public List<Item> findAll(){
        return em.createQuery("select i from item i", Item.class).getResultList();
    }
}