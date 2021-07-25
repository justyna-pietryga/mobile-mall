package com.mobilemall.persistence.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long category_id;
    private String originalName;

    @ManyToOne
    @JoinColumn(name="standardCategory_id")
    private StandardCategory standardCategory;

    private String url;
    @ManyToOne
    @JoinColumn(name = "shop_id", nullable = false)
    private Shop shop;

    public Category(String originalName, String url, Shop shop) {
        this.originalName = originalName;
        this.url = url;
        this.shop = shop;
    }
}
