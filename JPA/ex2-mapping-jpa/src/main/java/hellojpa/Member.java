package hellojpa;

import jakarta.persistence.*;

import java.util.Date;

/**
 * @AUTHOR dyd71
 * @DATE 2024-10-23
 * @PARAM
 * @VERSION 1.0
 */

@Entity
// 테이블 이름 매핑
//@Table(name = "users")
public class Member {
    @Id
    private Long id;
    @Column(name = "name", updatable = false, nullable = false, columnDefinition = "varchar(100)")
    private String username;

    private Integer age;

    // enum type 필수적으로 EnumType.STRING 타입을 쓸것
    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    // 날짜 => 날짜, 시간, 날짜+시간
    // LocalDate LocalDateTime을 쓰면됌 옛날 버전 에서는 유용할 수 있지만...
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;
    // clob, blob
    @Lob
    private String description;

    // 필드에서 제외 메모리에서만 사용하는 필드
    @Transient
    private int temp;

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }

    public Member() {
    }

}
