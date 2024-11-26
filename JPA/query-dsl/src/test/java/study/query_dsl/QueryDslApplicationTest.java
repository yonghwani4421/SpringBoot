package study.query_dsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.query_dsl.entity.Hello;
import study.query_dsl.entity.QHello;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class QueryDslApplicationTest {
    @Autowired
    EntityManager em;

    @Test
    @DisplayName("querydsl test")
    public void querydslTest() throws Exception{
        // given

        Hello hello = new Hello();
        em.persist(hello);
        // when

        JPAQueryFactory query = new JPAQueryFactory(em);
        QHello qHello = QHello.hello; //Querydsl Q타입 동작 확인
        Hello result = query
                .selectFrom(qHello)
                .fetchOne();

        // then
        Assertions.assertThat(result).isEqualTo(hello);
        //lombok 동작 확인 (hello.getId())
        Assertions.assertThat(result.getId()).isEqualTo(hello.getId());




    }
}