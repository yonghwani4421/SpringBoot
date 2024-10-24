package hellojpa;

import jakarta.persistence.*;

import java.time.LocalDateTime;

/**
 * @AUTHOR dyd71
 * @DATE 2024-10-24
 * @PARAM
 * @VERSION 1.0
 */
@Entity
public class MemberProduct {
    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;


    private int count;
    private int price;
    private LocalDateTime orderDateTime;


}
