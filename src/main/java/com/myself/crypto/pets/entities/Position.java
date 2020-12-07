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

    @Column(name = "currency_id")
    private Float currencyId;

    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn (name="portfolio_id")
    private Portfolio portfolio;

    public Position(Long id, Long amount, Float currencyId) {
        this.id = id;
        this.amount = amount;
        this.currencyId = currencyId;
    }
}
