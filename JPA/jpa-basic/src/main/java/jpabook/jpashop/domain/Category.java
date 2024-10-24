package jpabook.jpashop.domain;

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
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    @ManyToOne
    @JoinColumn(name = "PARENT_ID")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>();


    @ManyToMany
    @JoinTable(name = "CATEGORY_ITEM"
                        ,joinColumns = @JoinColumn(name="CATEGORY_ID")
                        ,inverseJoinColumns = @JoinColumn(name = "ITEM_ID")
    )
    private List<Item> items = new ArrayList<>();

}
