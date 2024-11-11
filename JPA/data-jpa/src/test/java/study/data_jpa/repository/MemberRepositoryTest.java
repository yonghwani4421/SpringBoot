package study.data_jpa.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.data_jpa.entity.Member;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(false)
class MemberRepositoryTest {
    @Autowired
    MemberRepository memberRepository;



    @Test
    @DisplayName("test member")
    public void testMember() throws Exception{
        // given
        Member member = new Member("memberA");
        Member saveMember = memberRepository.save(member);


        // when
        Member findMember = memberRepository.findById(saveMember.getId()).get();

        // then
        assertThat(findMember.getId()).isEqualTo(member.getId());
        assertThat(findMember).isEqualTo(member);
    }

}