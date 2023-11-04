package com.example.geodevineur;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Index{
    /* Redirection to welcome page */
    @GetMapping("/")
    public String index(Model model) {
        return "index";
    }

    /* Redirection to rules page */
    @GetMapping("regles")
    public String regles(Model model) {
        return "regles";
    }
}
