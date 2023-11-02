package com.example.geodevineur;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Index{
    //Redirection sur la page d'accueil
    @GetMapping("/")
    public String index(Model model) {
        return "index";
    }

    //Redirection sur la page de regles
    @GetMapping("regles")
    public String regles(Model model) {
        return "regles";
    }
}
