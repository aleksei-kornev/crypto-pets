package com.myself.crypto.pets.repositories;

import com.myself.crypto.pets.entities.Currency;
import com.myself.crypto.pets.entities.Portfolio;
import com.myself.crypto.pets.entities.dtos.CurrencyDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PortfoliosRepository extends JpaRepository<Portfolio, Long> {
    //List<Portfolio> findAll();
}