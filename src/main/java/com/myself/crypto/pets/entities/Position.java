package com.myself.crypto.pets.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "positions")
@Data
@NoArgsConstructor
public class Position {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "amount")
    private Long amount;

    @Column(name = "id_currency")
    private float idCurrency;

    @ManyToOne
    @JoinColumn (name="portfolio_id")
    private Portfolio portfolio;

    public Position(Long id, Long amount, float idCurrency) {
        this.id = id;
        this.amount = amount;
        this.idCurrency = idCurrency;
    }
}
