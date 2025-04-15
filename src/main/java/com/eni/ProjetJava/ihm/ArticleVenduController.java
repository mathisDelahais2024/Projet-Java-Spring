package com.eni.ProjetJava.ihm;

import com.eni.ProjetJava.bo.ArticleVendu;
import com.eni.ProjetJava.service.ArticleVenduService;
import com.eni.ProjetJava.service.ReponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.eni.ProjetJava.service.ConstanteService.*;

@Controller
@RequestMapping("/article-vendu")
public class ArticleVenduController {

    @Autowired
    ArticleVenduService articleVenduService;

    @GetMapping("/getAll")
    public String getArticleVendu(Model model) {
        ReponseService<List<ArticleVendu>> reponseService = articleVenduService.getAll();
        return "articles-vendus.html";
    }

    @GetMapping("/getById")
    public String getArticleVenduById(@RequestParam("noArticle") String noArticle, Model model) {
        ReponseService<ArticleVendu> reponseService = articleVenduService.getByNoArticle(noArticle);
        if (reponseService.getCode().equals(CD_ERR_NOT_FOUND)) {
            return "error-page.html";
        }
        return "article-details.html";
    }

    @PostMapping("/vendre")
    public String vendreArticle(@ModelAttribute ArticleVendu article, Model model) {
        ReponseService<ArticleVendu> reponseService = articleVenduService.vendreArticle(article);
        if (!reponseService.getCode().equals(CD_CREATED)) {
            return "vendre-article.html";
        }
        return "confirmation-vente.html";
    }

}
