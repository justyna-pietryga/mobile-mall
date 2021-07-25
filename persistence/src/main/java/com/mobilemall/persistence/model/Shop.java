package com.mobilemall.persistence.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
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
    @Transient
    private List<Category> categories;
}
