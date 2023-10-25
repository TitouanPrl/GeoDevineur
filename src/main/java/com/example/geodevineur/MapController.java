package com.example.geodevineur;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MapController{
    @GetMapping("/apprendre-departements")
    public String apprendreDep(Model model) {
        String numeroDep = "00";
        //model add select pour chacun des departements de la bdd
        String maVariableJava = "Ceci est une variable Java.";
        model.addAttribute("maVariableJava", maVariableJava);
        model.addAttribute("numeroDep", numeroDep);
        return "apprendre-departements"; // renvoie le nom de votre fichier HTML
    }

    @GetMapping("/apprendre-regions")
    public String apprendreReg(Model model) {
        String numeroDep = "00";
        //model add select pour chacun des departements de la bdd
        String maVariableJava = "Ceci est une variable Java.";
        model.addAttribute("maVariableJava", maVariableJava);
        model.addAttribute("numeroDep", numeroDep);
        return "apprendre-regions"; // renvoie le nom de votre fichier HTML
    }


}
