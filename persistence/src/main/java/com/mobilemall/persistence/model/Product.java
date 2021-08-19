package com.mobilemall.persistence.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "mall_order_id")
    private MallOrder mallOrder;

    private String name;
    private double price;
    private String url;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "shop_id")
    private Shop shop;

    public Product(String name, double price, String url, Shop shop) {
        this.name = name;
        this.price = price;
        this.url = url;
        this.shop = shop;
    }
}
