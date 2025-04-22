package com.eni.ProjetJava.ihm.controller;

import com.eni.ProjetJava.bo.Article;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ControlleurEnchere {

    @GetMapping("/accueil")
    public String listerEncheres(Model model) {
        List<Article> articles = new ArrayList<>();

        articles.add(new Article(
                "PC Gamer pour travailler",
                210,
                2,
                LocalDate.of(2018, 8, 10),
                "10 allée des Alouettes, 44800 Saint Herblain",
                "jojo44",
                "https://via.placeholder.com/150"
        ));

        articles.add(new Article(
                "Rocket stove pour riz et pâtes",
                185,
                3,
                LocalDate.of(2018, 10, 9),
                "5 rue des Pinsons, 44000 Nantes",
                "NineJea",
                "https://via.placeholder.com/150"
        ));

        model.addAttribute("articles", articles);
        return "accueil";
    }
}
