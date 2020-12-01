package com.myself.crypto.pets.services;

import com.myself.crypto.pets.entities.Currency;
import com.myself.crypto.pets.entities.dtos.CurrencyDto;
import com.myself.crypto.pets.exceptions.CurrencyNotFoundException;
import com.myself.crypto.pets.parser.Parser;
import com.myself.crypto.pets.repositories.CurrenciesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class CurrenciesService {
    private CurrenciesRepository currenciesRepository;

    @Autowired
    public void setCurrenciesRepository(CurrenciesRepository currenciesRepository) {
        this.currenciesRepository = currenciesRepository;
    }

    public Currency saveOrUpdate(Currency currency) {
        return currenciesRepository.save(currency);
    }

    public Currency findById(Long id) {
        return currenciesRepository.findById(id).orElseThrow(() -> new CurrencyNotFoundException("Can't found currency with id = " + id));
    }

    public Currency findByName(String name) {
        return currenciesRepository.findByTicker(name);
    }

    public List<Currency> updateAllPrices () {
        return currenciesRepository.saveAll(Parser.updateAllCurrenciesPricesFromApi(currenciesRepository.findAll()));
    }

    public List<Currency> findAll() {
        return currenciesRepository.findAll();
    }

    public Page<Currency> findAll(Specification<Currency> spec, Integer page) {
        if (page < 1L) {
            page = 1;
        }
        return currenciesRepository.findAll(spec, PageRequest.of(page - 1, 10));
    }

    public void deleteAll() {
        currenciesRepository.deleteAll();
    }

    public void deleteById(Long id) {
        currenciesRepository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return currenciesRepository.existsById(id);
    }

    public List<CurrencyDto> getDtoData() {
        return currenciesRepository.findAllBy();
    }

}