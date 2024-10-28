package jpql.domain;

import jakarta.persistence.*;

/**
 * @AUTHOR dyd71
 * @DATE 2024-10-28
 * @PARAM
 * @VERSION 1.0
 */
@Entity
public class Member {
    @Id @GeneratedValue
    private Long id;
    private String username;
    private int age;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TEAM_ID")
    private Team team;

    @Enumerated(EnumType.STRING)
    private MemberType type;

    public MemberType getMemberType() {
        return type;
    }

    public void setMemberType(MemberType type) {
        this.type = type;
    }

    public void chnageTeam(Team team){
        this.team = team;
        team.getMembers().add(this);
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", age=" + age +
                '}';
    }
}
