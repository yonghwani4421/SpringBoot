package hellojpa;

import jakarta.persistence.*;

/**
 * @AUTHOR dyd71
 * @DATE 2024-10-23
 * @PARAM
 * @VERSION 1.0
 */

@Entity
public class Member {
    @Id @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;
    @Column(name = "USERNAME")
    private String username;

//    @Column(name = "TEAM_ID")
//    private Long teamId;


    // 연관관계 매핑 ( 연관관계의 주인 값 변경 삭제 가능 )
    // 연관관계의 주인은 외래키가 있는 쪽으로 정한다.
    @ManyToOne
    @JoinColumn(name = "TEAM_ID")
    private Team team;


    public Member() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Team getTeam() {
        return team;
    }

    // 실수 없게 여기서 넣어주자
    public void changeTeam(Team team) {
        this.team = team;
        team.getMembers().add(this);
    }





}
