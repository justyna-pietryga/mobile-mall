package com.mobilemall.persistence.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Data
@NoArgsConstructor
public class MallUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long mall_user_id;
    private String name;
    private String lastName;
    @Column(unique = true)
    private String email;

    public MallUser(String name, String lastName, String email) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
    }

    @Transient
    @OneToMany(mappedBy="mallUser")
    private Collection<MallOrder> orders;
}
