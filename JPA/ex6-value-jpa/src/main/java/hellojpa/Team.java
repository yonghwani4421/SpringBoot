package hellojpa;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @AUTHOR dyd71
 * @DATE 2024-10-24
 * @PARAM
 * @VERSION 1.0
 */
@Entity
public class Team {

    @Id @GeneratedValue
    @Column(name = "TEAM_ID")
    private Long id;

    private String name;

    // 데이터 읽기만 가능
//    @OneToMany
//    @JoinColumn(name = "TEAM_ID")
//    private List<Member> members = new ArrayList<>();

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

//    public List<Member> getMembers() {
//        return members;
//    }
//
//    public void setMembers(List<Member> members) {
//        this.members = members;
//    }

}
