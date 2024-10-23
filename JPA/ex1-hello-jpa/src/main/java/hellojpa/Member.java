package hellojpa;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

/**
 * @AUTHOR dyd71
 * @DATE 2024-10-23
 * @PARAM
 * @VERSION 1.0
 */

@Entity
public class Member {
    @Id
    private Long id;
    private String name;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Member() {
    }

    public Member(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
