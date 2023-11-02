package com.example.geodevineur;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Index{
    @GetMapping("/")
    public String index(Model model) {
        return "index";
    }

    @GetMapping("regles")
    public String regles(Model model) {
        return "regles";
    }

    @GetMapping("quizz")
    public String quizzDepartements(Model model) {
        return "quizz";
    }
}
