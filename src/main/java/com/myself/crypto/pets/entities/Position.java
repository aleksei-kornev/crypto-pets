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

    @OneToOne (fetch = FetchType.EAGER)
    @JoinColumn (name="currency_id")
    private Currency coin;

    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn (name="portfolio_id")
    private Portfolio portfolio;

    public Position(Long id, Long amount) {
        this.id = id;
        this.amount = amount;
    }

    public Position(Long id, Long amount, Portfolio portfolio, Currency coin) {
        this.id = id;
        this.amount = amount;
        this.portfolio = portfolio;
        this.coin = coin;
    }
}
