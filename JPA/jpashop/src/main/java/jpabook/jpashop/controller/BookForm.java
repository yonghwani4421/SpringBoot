package jpabook.jpashop.controller;

import lombok.Getter;
import lombok.Setter;

/**
 * @AUTHOR dyd71
 * @DATE 2024-11-01
 * @PARAM
 * @VERSION 1.0
 */
@Setter
@Getter
public class BookForm {
    private Long id;
    private String name;
    private int price;
    private int stockQuantity;
    private String author;
    private String isbn;

}
