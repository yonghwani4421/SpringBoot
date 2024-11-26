package study.data_jpa.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import study.data_jpa.entity.Item;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ItemRepositoryTest {

    @Autowired ItemRepository itemRepository;

    @Test
    @DisplayName("item test")
    public void ItemRepositoryTest() throws Exception{
        // given

        Item item = new Item("A");
        itemRepository.save(item);

        // when

        // then
    }
}