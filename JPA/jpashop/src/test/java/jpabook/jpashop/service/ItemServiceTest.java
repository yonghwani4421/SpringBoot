package jpabook.jpashop.service;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional

public class ItemServiceTest {
    @Autowired
    ItemService itemService;
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    EntityManager em;

    @Test
    @DisplayName("상품등록")
    public void 상품등록() throws Exception{
        // given
        Book book = new Book();
        book.setAuthor("작가");
        book.setIsbn("1234");

        // when
        itemService.saveItem(book);

        // then
//        em.flush();
        assertEquals(book, itemRepository.findOne(book.getId()));
    }
    @Test
    @DisplayName("상품리스트 조회")
    public void 상품리스트조회() throws Exception{
        // given
        Book book = new Book();
        book.setAuthor("작가");
        book.setIsbn("1234");

        // when
        itemService.saveItem(book);

        // then
//        em.flush();
        assertEquals(book, itemRepository.findAll().get(0));
    }

    @Test
    @DisplayName("상품수정")
    public void 상품수정() throws Exception{
        // given
        Book book = new Book();
        book.setAuthor("작가");
        book.setIsbn("1234");

        itemService.saveItem(book);
//        em.flush();
        // when
        Book findBook = (Book) itemService.findOne(book.getId());

        String changeAuthor = "작가2";
        findBook.setAuthor(changeAuthor);


//        em.flush();


        // then
        assertEquals(changeAuthor, findBook.getAuthor());
    }
}