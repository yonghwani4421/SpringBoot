package study.query_dsl.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import study.query_dsl.dto.MemberSearchCondition;
import study.query_dsl.dto.MemberTeamDto;

import java.util.List;

public interface MemberRepositoryCustom {
    List<MemberTeamDto> search(MemberSearchCondition condition);
    Page<MemberTeamDto> searchPageSimple(MemberSearchCondition condition, Pageable pageable);
    Page<MemberTeamDto> searchPageComplex(MemberSearchCondition condition, Pageable pageable);

}
