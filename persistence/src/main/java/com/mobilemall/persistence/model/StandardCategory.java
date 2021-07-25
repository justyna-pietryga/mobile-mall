package com.mobilemall.persistence.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
public class StandardCategory {
    @Id
    @GeneratedValue
    private Long standardCategory_id;
    private String name;

    @Transient
    @OneToMany(mappedBy="standardCategory")
    private Set<Category> categories;

    public StandardCategory(String name) {
        this.name = name;
    }
}
