package com.mobilemall.persistence.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
public class StandardCategory {
    @Id
    @GeneratedValue
    private Long standardCategory_id;
    private String name;

    @OneToMany(mappedBy="standardCategory")
    private Set<Category> categories;
}
