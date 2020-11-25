package com.myself.crypto.pets.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myself.crypto.pets.entities.Category;
import com.myself.crypto.pets.entities.Currency;
import com.myself.crypto.pets.entities.dtos.CurrencyDto;
import com.myself.crypto.pets.exceptions.CurrencyNotFoundException;
import com.myself.crypto.pets.services.CategoriesService;
import com.myself.crypto.pets.services.CurrenciesService;
import com.myself.crypto.pets.utils.CurrencyFilter;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/currencies")
public class CurrenciesController {
    private CurrenciesService currenciesService;
    private CategoriesService categoriesService;

    @Autowired
    public CurrenciesController(CurrenciesService currenciesService, CategoriesService categoriesService) {
        this.currenciesService = currenciesService;
        this.categoriesService = categoriesService;
    }

    @GetMapping
    public String showAll(Model model, @RequestParam Map<String, String> requestParams, @RequestParam(name = "categories", required = false) List<Long> categoriesIds) {
        Integer pageNumber = Integer.parseInt(requestParams.getOrDefault("p", "1"));

        List<Category> categoriesFilter = null;
        if (categoriesIds != null) {
            categoriesFilter = categoriesService.getCategoriesByIds(categoriesIds);
        }
        CurrencyFilter currencyFilter = new CurrencyFilter(requestParams, categoriesFilter);
        Page<Currency> currencies = currenciesService.findAll(currencyFilter.getSpec(), pageNumber);
        model.addAttribute("currencies", currencies);
        model.addAttribute("filterDef", currencyFilter.getFilterDefinition().toString());
        return "all_currencies";
    }

//    @GetMapping("/updateCurrencies")
//    public String updateCurrenciesFromApi() {
//        currenciesService.updateCurrenciesFromApi();
//        return "redirect:/currencies/";
//    }

    @GetMapping(path = "/updatePrice")
    public String updatePrice() {
        currenciesService.updatePrice("ETH");
        return "redirect:/currencies/";
    }

    @GetMapping("/add")
    public String showAddForm() {
        return "add_currency_form";
    }

    @PostMapping("/add")
    public String saveNewCurrency(@ModelAttribute Currency currency) {
        currenciesService.saveOrUpdate(currency);
        return "redirect:/currencies/";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("currency", currenciesService.findById(id));
        return "edit_currency_form";
    }

    @PostMapping("/edit")
    public String modifyCurrency(@ModelAttribute Currency currency) {
        currenciesService.saveOrUpdate(currency);
        return "redirect:/currencies/";
    }
}