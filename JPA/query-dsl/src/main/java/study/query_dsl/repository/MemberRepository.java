package study.query_dsl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.query_dsl.entity.Member;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {
    List<Member> findByUsername(String username);
}
