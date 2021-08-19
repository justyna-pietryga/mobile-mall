package com.mobilemall.persistence.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
public class MallOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long mall_order_id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "mall_user_id")
    private MallUser mallUser;

    private Instant orderDate = Instant.now();

    @Transient
    @OneToMany(mappedBy = "mallOrder")
    private List<Product> products;

    private double price;

    public void setProducts(List<Product> products) {
        this.products = products;
        this.price = products.stream()
                .mapToDouble(Product::getPrice)
                .sum();
    }
}
