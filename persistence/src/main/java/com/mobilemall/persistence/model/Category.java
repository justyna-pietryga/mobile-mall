package com.mobilemall.persistence.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long category_id;
    private String name;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="standardCategory_id")
    private StandardCategory standardCategory;

    private String url;
    @ManyToOne
    @JoinColumn(name = "shop_id", nullable = false)
    private Shop shop;

    public Category(String name, String url, Shop shop) {
        this.name = name;
        this.url = url;
        this.shop = shop;
    }
}
