package com.myself.crypto.pets.controllers;

import com.myself.crypto.pets.entities.Category;
import com.myself.crypto.pets.entities.Currency;
import com.myself.crypto.pets.entities.Portfolio;
import com.myself.crypto.pets.entities.Position;
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
    private CurrenciesService currenciesService;
    //private CategoriesService categoriesService;

    @Autowired
    public PortfoliosController(PortfoliosService portfoliosService, CurrenciesService currenciesService) {
        this.portfoliosService = portfoliosService;
        this.currenciesService = currenciesService;
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
        List<Currency> currencies = currenciesService.findAll();
        model.addAttribute("portfolio", portfolio);
        model.addAttribute("currencies", currencies);
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
    @PostMapping("/add")
    public String saveNewPosition(@RequestParam("coinId") String coinId, @RequestParam("portfolioId") Long portfolioId, @RequestParam("amount") Long amount) {
        if ( (Long.parseLong(coinId) <= 0) || (portfolioId <= 0) || (amount <= 0)) return "redirect:/portfolios/";
        Currency coin = currenciesService.findById(Long.parseLong(coinId));
        Portfolio portfolio = portfoliosService.findById(portfolioId);
        Position position = new Position(null,amount, portfolio, coin);
        portfoliosService.addPosition(portfolioId,position);
        return "redirect:/portfolios/view/"+portfolioId;
    }


    @GetMapping("/delete/{id}")
    public String deletePortfolio(@PathVariable Long id, Model model) {
        if (portfoliosService.findById(id) != null) {
            portfoliosService.deleteById(id);
        }
        return "redirect:/portfolios/";
    }

    @GetMapping("/create/")
    public String createNewPortfolio() {
        Long portfolioId = portfoliosService.createNewPortfolio(1L).getId();
        return "redirect:/portfolios/view/"+portfolioId;
    }

    @GetMapping("/edit")
    public String showEditForms(Model model, @RequestParam Map<String, String> requestParams) {
        if (requestParams.containsKey("delete") && Boolean.parseBoolean(requestParams.get("delete")) && requestParams.containsKey("position") && requestParams.containsKey("portfolio")) {
            portfoliosService.deletePositionById(Long.parseLong(requestParams.get("position")));
            portfoliosService.recalculateCost(Long.parseLong(requestParams.get("portfolio")));
            return "redirect:/portfolios/view/"+requestParams.get("portfolio");
        } else {
            //model.addAttribute("portfolio", portfoliosService.findById(Long.getLong(requestParams.get("portfolio"))));
            return "redirect:/portfolios/view/"+requestParams.get("portfolio");
            //return "edit_currency_form";
        }
    }

//    @PostMapping("/edit")
//    public String modifyCurrency(@ModelAttribute Currency currency) {
//        currenciesService.saveOrUpdate(currency);
//        return "redirect:/currencies/";
//    }
}