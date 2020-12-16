package com.myself.crypto.pets.services;

import com.myself.crypto.pets.entities.Currency;
import com.myself.crypto.pets.entities.Portfolio;
import com.myself.crypto.pets.entities.Position;
import com.myself.crypto.pets.entities.dtos.CurrencyDto;
import com.myself.crypto.pets.exceptions.CurrencyNotFoundException;
import com.myself.crypto.pets.parser.Parser;
import com.myself.crypto.pets.repositories.CurrenciesRepository;
import com.myself.crypto.pets.repositories.PortfoliosRepository;
import com.myself.crypto.pets.repositories.PositionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.sound.sampled.Port;
import java.util.List;

@Service
public class PortfoliosService {
    private PortfoliosRepository portfoliosRepository;
    private PositionsRepository positionsRepository;

    @Autowired
    public void setPortfoliosRepository(PortfoliosRepository portfoliosRepository, PositionsRepository positionsRepository) {
        this.portfoliosRepository = portfoliosRepository;
        this.positionsRepository = positionsRepository;
    }

    public Portfolio saveOrUpdate(Portfolio portfolio) {
        return portfoliosRepository.save(portfolio);
    }

    public Portfolio recalculateCost(Long id) {
        Float newCost = 0F;
        Portfolio portfolio = findById(id);
        for (Position position : portfolio.getPositions()) {
            newCost += position.getAmount() * position.getCoin().getUSD();
        }
        portfolio.setCost(newCost);
        return saveOrUpdate(portfolio);
    }

    //Подправить исключение на специальное для портфелей
    public Portfolio findById(Long id) {
        return portfoliosRepository.findById(id).orElseThrow(() -> new CurrencyNotFoundException("Can't found portfolio with id = " + id));
    }

    public Portfolio addPosition (Long portfolioId, Position position) {
        Portfolio portfolio = findById(portfolioId);
        List<Position> listPositions = portfolio.getPositions();
        listPositions.add(position);
        portfolio.setPositions(listPositions);
        saveOrUpdate(portfolio);
        return recalculateCost(portfolio.getId());
    }

    public List<Portfolio> findAll() {
        return portfoliosRepository.findAll();
    }

//    public Page<Portfolio> findAll(Specification<Portfolio> spec, Integer page) {
//        if (page < 1L) {
//            page = 1;
//        }
//        return portfoliosRepository.findAll(spec, PageRequest.of(page - 1, 10));
//    }

//    public void deleteAll() {
//        portfoliosRepository.deleteAll();
//    }

    public void deleteById(Long id) {
        portfoliosRepository.deleteById(id);
    }

    public void deletePositionById(Long idPosition) {
        positionsRepository.deleteById(idPosition);
    }

//    public boolean existsById(Long id) {
//        return portfoliosRepository.existsById(id);
//    }

}