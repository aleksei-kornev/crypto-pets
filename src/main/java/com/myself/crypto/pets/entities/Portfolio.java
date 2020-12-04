package com.myself.crypto.pets.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "portfolios")
@Data
@NoArgsConstructor
public class Portfolio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "cost")
    private float cost;

    @Column(name = "last_update")
    private long lastUpdate;

    @OneToMany
    private List<Position> positions;

    public Portfolio(Long id, Long userId, float cost, long lastUpdate) {
        this.id = id;
        this.userId = userId;
        this.cost = cost;
        this.lastUpdate = lastUpdate;
    }
}
