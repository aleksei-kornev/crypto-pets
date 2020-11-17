package com.myself.crypto.pets.controllers;

import com.myself.crypto.pets.entities.Currency;
import com.myself.crypto.pets.entities.dtos.CurrencyDto;
import com.myself.crypto.pets.exceptions.CurrencyNotFoundException;
import com.myself.crypto.pets.services.CurrenciesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/currencies")
@Api("Set of endpoints for CRUD operations for Currencies")
public class RestCurrenciesController {
    private CurrenciesService currenciesService;

    @Autowired
    public RestCurrenciesController(CurrenciesService currenciesService) {
        this.currenciesService = currenciesService;
    }

    @GetMapping("/dto")
    @ApiOperation("Returns list of all currencies data transfer objects")
    public List<CurrencyDto> getAllCurrenciesDto() {
        return currenciesService.getDtoData();
    }

    @GetMapping(produces = "application/json")
    @ApiOperation("Returns list of all currencies")
    public List<Currency> getAllCurrencies() {
        return currenciesService.findAll();
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    @ApiOperation("Returns one currency by id")
    public ResponseEntity<?> getOneCurrency(@PathVariable @ApiParam("Id of the currency to be requested. Cannot be empty") Long id) {
        if (!currenciesService.existsById(id)) {
            throw new CurrencyNotFoundException("Currency not found, id: " + id);
        }
        return new ResponseEntity<>(currenciesService.findById(id), HttpStatus.OK);
    }

    @DeleteMapping
    @ApiOperation("Removes all currencies")
    public void deleteAllCurrencies() {
        currenciesService.deleteAll();
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Removes one currency by id")
    public void deleteOneCurrencies(@PathVariable Long id) {
        currenciesService.deleteById(id);
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Creates a new currency")
    public Currency saveNewCurrency(@RequestBody Currency currency) {
        if (currency.getId() != null) {
            currency.setId(null);
        }
        return currenciesService.saveOrUpdate(currency);
    }

    @PutMapping(consumes = "application/json", produces = "application/json")
    @ApiOperation("Modifies an existing currency")
    public ResponseEntity<?> modifyCurrency(@RequestBody Currency currency) {
        if (currency.getId() == null || !currenciesService.existsById(currency.getId())) {
            throw new CurrencyNotFoundException("Currency not found, id: " + currency.getId());
        }
        if (currency.getPrice() < 0) {
            return new ResponseEntity<>("Currency's price can not be negative", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(currenciesService.saveOrUpdate(currency), HttpStatus.OK);
    }

    @ExceptionHandler
    public ResponseEntity<?> handleException(CurrencyNotFoundException exc) {
        return new ResponseEntity<>(exc.getMessage(), HttpStatus.NOT_FOUND);
    }
}