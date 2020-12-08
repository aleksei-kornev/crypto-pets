package com.myself.crypto.pets.services;

import com.myself.crypto.pets.entities.Currency;
import com.myself.crypto.pets.entities.Portfolio;
import com.myself.crypto.pets.entities.dtos.CurrencyDto;
import com.myself.crypto.pets.exceptions.CurrencyNotFoundException;
import com.myself.crypto.pets.parser.Parser;
import com.myself.crypto.pets.repositories.CurrenciesRepository;
import com.myself.crypto.pets.repositories.PortfoliosRepository;
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
    private CurrenciesRepository currenciesRepository;

    @Autowired
    public void setPortfoliosRepository(PortfoliosRepository portfoliosRepository, CurrenciesRepository currenciesRepository) {
        this.portfoliosRepository = portfoliosRepository;
        this.currenciesRepository = currenciesRepository;
    }

//    public Portfolio saveOrUpdate(Portfolio portfolio) {
//        return portfoliosRepository.save(portfolio);
//    }

    //Подправить исключение на специальное для портфелей
    public Portfolio findById(Long id) {
        return portfoliosRepository.findById(id).orElseThrow(() -> new CurrencyNotFoundException("Can't found portfolio with id = " + id));
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
//
//    public void deleteById(Long id) {
//        portfoliosRepository.deleteById(id);
//    }
//
//    public boolean existsById(Long id) {
//        return portfoliosRepository.existsById(id);
//    }

}