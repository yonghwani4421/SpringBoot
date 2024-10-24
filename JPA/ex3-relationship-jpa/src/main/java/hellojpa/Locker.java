package hellojpa;

import jakarta.persistence.*;
import org.hibernate.query.sql.internal.ParameterRecognizerImpl;

/**
 * @AUTHOR dyd71
 * @DATE 2024-10-24
 * @PARAM
 * @VERSION 1.0
 */
@Entity
public class Locker {
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(mappedBy = "locker")
    private Member member;

    private String name;


}
