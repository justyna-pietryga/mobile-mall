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

    public Shop(String name) {
        this.shop_id = name;
    }

    @Id
    private String shop_id;

    @OneToMany(mappedBy = "shop")
    private List<Category> categories;
}
