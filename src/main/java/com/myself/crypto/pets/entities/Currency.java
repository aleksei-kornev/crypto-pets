package com.myself.crypto.pets.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "currencies")
@Data
@NoArgsConstructor
public class Currency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "USD")
    private float USD;

    @ManyToMany
    @JoinTable(name = "currencies_categories",
            joinColumns = @JoinColumn(name = "currency_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private List<Category> categories;

    public Currency(Long id, String title, String name, float USD, String description) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.USD = USD;
        this.description = description;
    }
}
