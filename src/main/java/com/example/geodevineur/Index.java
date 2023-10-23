package com.example.geodevineur;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Index{
    @GetMapping("/")
    public String index(Model model) {
        return "index";
    }

    @GetMapping("/regles")
    public String regles(Model model) {

        return "regles";
    }

    @GetMapping("/scores")
    public String scores(Model model) {
        return "scores";
    }

    @GetMapping("/quizz-departements")
    public String quizzDepartements(Model model) {
        return "quizz-departements";
    }

    @GetMapping("/quizz-regions")
    public String quizzRegions(Model model) {
        return "quizz-regions";
    }
}
