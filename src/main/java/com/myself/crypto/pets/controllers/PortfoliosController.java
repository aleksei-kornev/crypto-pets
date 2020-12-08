package com.myself.crypto.pets.controllers;

import com.myself.crypto.pets.entities.Category;
import com.myself.crypto.pets.entities.Currency;
import com.myself.crypto.pets.entities.Portfolio;
import com.myself.crypto.pets.services.CategoriesService;
import com.myself.crypto.pets.services.CurrenciesService;
import com.myself.crypto.pets.services.PortfoliosService;
import com.myself.crypto.pets.utils.CurrencyFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/portfolios")
public class PortfoliosController {
    private PortfoliosService portfoliosService;
    //private CategoriesService categoriesService;

    @Autowired
    public PortfoliosController(PortfoliosService portfoliosService) {
        this.portfoliosService = portfoliosService;
        //this.categoriesService = categoriesService;
    }

    @GetMapping
    public String showAll(Model model, @RequestParam Map<String, String> requestParams) {
        //Integer pageNumber = Integer.parseInt(requestParams.getOrDefault("p", "1"));

        List<Portfolio> portfolios = portfoliosService.findAll();
        model.addAttribute("portfolios", portfolios);

        return "all_portfolios";
    }

    @GetMapping (path = "/view/{id}")
    public String showPortfolio(@PathVariable Long id, Model model) {
        Portfolio portfolio = portfoliosService.findById(id);
        model.addAttribute("portfolio", portfolio);
        return "view_portfolio";
    }

//    @GetMapping(path = "/updatePrices")
//    public String updateAllPrices() {
//        currenciesService.updateAllPrices();
//        return "redirect:/currencies/";
//    }
//
//    @GetMapping("/add")
//    public String showAddForm() {
//        return "add_currency_form";
//    }
//
//    @PostMapping("/add")
//    public String saveNewCurrency(@ModelAttribute Currency currency) {
//        currenciesService.saveOrUpdate(currency);
//        return "redirect:/currencies/";
//    }
//
//    @GetMapping("/edit/{id}")
//    public String showEditForm(@PathVariable Long id, Model model) {
//        model.addAttribute("currency", currenciesService.findById(id));
//        return "edit_currency_form";
//    }
//
//    @PostMapping("/edit")
//    public String modifyCurrency(@ModelAttribute Currency currency) {
//        currenciesService.saveOrUpdate(currency);
//        return "redirect:/currencies/";
//    }
}