package com.example.geodevineur;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;

@Controller
public class MapController{
    @GetMapping("apprendre-departements")
    public String apprendreDep(Model model) {
        ArrayList<String> allDepartements = new ArrayList<>();
        for (int i = 0; i <= 9; i++) {
            for (int j = 0; j <= 9; j++) {
                allDepartements.add(String.format("%s%s", i, j));
            }
        }
        for (int i = 0; i < 4; i++) {
            allDepartements.remove(96);
        }
        allDepartements.remove(20);
        allDepartements.add(20,"2B");
        allDepartements.add(20,"2A");

        StringBuilder selectContent = new StringBuilder();
        selectContent.append("<select class=\"custom-select\" name=\"departements\" id=\"departement-select\">");
        for (String option : allDepartements) {
            selectContent.append("<option value=\"").append(option).append("\">").append(option).append("</option>");
        }
        selectContent.append("</select>");
        model.addAttribute("selectContent", selectContent);
        model.addAttribute("allDepartements", allDepartements);
        return "apprendre-departements";
    }

    @GetMapping("apprendre-regions")
    public String apprendreReg(Model model) {
        String numeroDep = "00";
        //model add select pour chacun des departements de la bdd
        String maVariableJava = "Ceci est une variable Java.";
        model.addAttribute("maVariableJava", maVariableJava);
        model.addAttribute("numeroDep", numeroDep);
        return "apprendre-regions"; // renvoie le nom de votre fichier HTML
    }


}
