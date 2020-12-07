package com.myself.crypto.pets.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
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

    @Column(name = "public")
    private boolean isPublic;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "cost")
    private Float cost;

    @Column(name = "last_update")
    private Timestamp lastUpdate;

    @OneToMany (
            mappedBy = "portfolio",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Position> positions;

    public Portfolio(Long id, Long userId, Float cost, Timestamp lastUpdate) {
        this.id = id;
        this.userId = userId;
        this.cost = cost;
        this.lastUpdate = lastUpdate;
    }
}
