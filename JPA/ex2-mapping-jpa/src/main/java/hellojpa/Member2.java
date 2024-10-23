package hellojpa;

import jakarta.persistence.*;

/**
 * @AUTHOR dyd71
 * @DATE 2024-10-23
 * @PARAM
 * @VERSION 1.0
 */
@Entity
@Table(name = "member")
public class Member2 {

    // 권장하는 식별자 전략
    // null 아님, 유일, 변하면 x
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // => Mysql auto increment DB에 위임
    private Long id;
    private String name;

    public Member2() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
