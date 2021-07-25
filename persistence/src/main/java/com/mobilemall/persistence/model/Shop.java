package com.mobilemall.persistence.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Shop {

    public Shop(String name, List<Category> categories) {
        this.name = name;
        this.categories = categories;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long shop_id;
    private String name;

    @OneToMany(mappedBy = "shop")
    private List<Category> categories;
}
