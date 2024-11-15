package study.data_jpa.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.data_jpa.dto.MemberDto;
import study.data_jpa.entity.Member;
import study.data_jpa.entity.Team;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(false)
class MemberRepositoryTest {
    @Autowired
    MemberRepository memberRepository;

    @Autowired
    TeamRepository teamRepository;


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


    @Test
    @DisplayName("basicCRUD")
    public void basicCRUD() throws Exception{
        // given
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");
        memberRepository.save(member1);
        memberRepository.save(member2);
        // when
        Member findMember1 = memberRepository.findById(member1.getId()).get();
        Member findMember2 = memberRepository.findById(member2.getId()).get();

        // then
        assertThat(findMember1).isEqualTo(member1);
        assertThat(findMember2).isEqualTo(member2);

        List<Member> all = memberRepository.findAll();
        assertThat(all.size()).isEqualTo(2);

        memberRepository.delete(member1);
        memberRepository.delete(member2);

        long count = memberRepository.count();
        assertThat(count).isEqualTo(0);
    }
    @Test
    @DisplayName("findByUsernameAndAgeGreaterThen")
    public void findByUsernameAndAgeGreaterThen() throws Exception{
        // given

        Member m1 = new Member("AAA", 10);
        Member m2 = new Member("AAA", 20);
        memberRepository.save(m1);
        memberRepository.save(m2);

        // when
        List<Member> result = memberRepository.findByUsernameAndAgeGreaterThan("AAA", 15);



        // then
        assertThat(result.get(0).getUsername()).isEqualTo("AAA");
        assertThat(result.get(0).getAge()).isEqualTo(20);
        assertThat(result.size()).isEqualTo(1);

    }


    @Test
    @DisplayName("testNameQuery")
    public void testNameQuery() throws Exception{
        Member m1 = new Member("AAA", 10);
        Member m2 = new Member("BBB", 20);
        memberRepository.save(m1);
        memberRepository.save(m2);
        // when
        List<Member> result = memberRepository.findByUsername("AAA");
        Member findMember = result.get(0);

        assertThat(findMember).isEqualTo(m1);
    }


    @Test
    @DisplayName("testQuery")
    public void testQuery() throws Exception{
        Member m1 = new Member("AAA", 10);
        Member m2 = new Member("BBB", 20);
        memberRepository.save(m1);
        memberRepository.save(m2);
        // when
        List<Member> result = memberRepository.findUser("AAA",10);
        Member findMember = result.get(0);

        assertThat(findMember).isEqualTo(m1);
    }


    @Test
    @DisplayName("")
    public void findUsernameList() throws Exception{
        // given
        Member m1 = new Member("AAA", 10);
        Member m2 = new Member("BBB", 20);
        memberRepository.save(m1);
        memberRepository.save(m2);
        // when

        List<String> usernameList = memberRepository.findUsernameList();


        for (String s : usernameList) {
            System.out.println("s = " + s);
        }

        // then
    }

    @Test
    @DisplayName("")
    public void findMemberDto() throws Exception{
        // given
        Team team = new Team("teamA");
        teamRepository.save(team);
        Member m1 = new Member("AAA", 10);
        m1.setTeam(team);
        memberRepository.save(m1);

        List<MemberDto> memberDto = memberRepository.findMemberDto();

        for (MemberDto dto : memberDto) {
            System.out.println("dto = " + dto);
        }

        // when

        // then
    }

    @Test
    @DisplayName("")
    public void findByNames() throws Exception{
        // given
        Member m1 = new Member("AAA", 10);
        Member m2 = new Member("BBB", 20);
        memberRepository.save(m1);
        memberRepository.save(m2);
        // when

        List<Member> members = memberRepository.findByNames(Arrays.asList("AAA", "BBB"));

        for (Member member : members) {
            System.out.println("member = " + member);
        }

        // then
    }

    @Test
    @DisplayName("")
    public void returnType() throws Exception{
        // given
        Member m1 = new Member("AAA", 10);
        Member m2 = new Member("BBB", 20);
        memberRepository.save(m1);
        memberRepository.save(m2);
        // when

        List<Member> memberList = memberRepository.findListByUsername("AAA");
        Member member = memberRepository.findMemberByUsername("AAA");
        Optional<Member> memberOptional = memberRepository.findOptionalByUsername("AAA");



        // then
    }

}