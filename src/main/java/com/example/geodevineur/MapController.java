package com.example.geodevineur;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;

@Controller
public class MapController{
    @GetMapping("apprendre-departements")
    public String apprendreDep(Model model) throws IOException {

        resetMapColors();

        ArrayList<String> allDepartements = new ArrayList<>();
        ArrayList<String> allNames = new ArrayList<>();
        for (int i = 0; i <= 9; i++) {
            for (int j = 0; j <= 9; j++) {
                allDepartements.add(String.format("%s%s", i, j));
            }
        }
        for (int i = 0; i < 4; i++) {
            allDepartements.remove(96);
        }
        allDepartements.remove(20);
        allDepartements.add(20,"2a");
        allDepartements.add(20,"2b");


        for (String departement : allDepartements) {
            allNames.add(departement + "- XX");
        }


        StringBuilder selectContent = new StringBuilder();
        selectContent.append("<select class=\"custom-select\" name=\"departements\" id=\"departement-select\">");
        int i=0;
        for (String option : allDepartements) {
            selectContent.append("<option value=\"").append(option).append("\">").append(allNames.get(i)).append("</option>");
            i++;
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

    @PostMapping("setDepartement")
    public String setDepartement(String departement) throws IOException, InterruptedException {
        ArrayList<String> nouvelleAquitaine = new ArrayList<String>(Arrays.asList("16","17","19","23","24","33","40","47","64","79","86","87"));
        ArrayList<String> bretagne = new ArrayList<String>(Arrays.asList("22","29","35","56"));
        ArrayList<String> hautsDeFrance = new ArrayList<String>(Arrays.asList("02","59","60","62","80"));
        ArrayList<String> occitanie = new ArrayList<String>(Arrays.asList("09","11","12","30","31","32","34","46","48","65","66","81","82"));
        ArrayList<String> normandie = new ArrayList<String>(Arrays.asList("14","27","50","61","76"));
        ArrayList<String> ileDeFrance = new ArrayList<String>(Arrays.asList("75","92","92","94","77","78","91","95"));
        ArrayList<String> grandEst = new ArrayList<String>(Arrays.asList("08","10","51","52","54","55","67","68","88"));
        ArrayList<String> paysDeLaLoire = new ArrayList<String>(Arrays.asList("44","49","53","72","85"));
        ArrayList<String> centreValDeLoire = new ArrayList<String>(Arrays.asList("18","28","36","37","41","45"));
        ArrayList<String> bourgogneFrancheComte = new ArrayList<String>(Arrays.asList("21","25","39","58","70","71","89","90"));
        ArrayList<String> auvergneRhoneAlpes = new ArrayList<String>(Arrays.asList("01","03","07","15","26","38","42","43","63","69","73","74"));
        ArrayList<String> provenceAlpesCoteAzur = new ArrayList<String>(Arrays.asList("04","05","06","13","83","84"));
        ArrayList<String> corse = new ArrayList<String>(Arrays.asList("2a","2b"));

        ArrayList<ArrayList<String>> regions = new ArrayList<ArrayList<String>>(Arrays.asList(nouvelleAquitaine,corse,bretagne,hautsDeFrance,occitanie,normandie,ileDeFrance,grandEst,paysDeLaLoire,centreValDeLoire,bourgogneFrancheComte,auvergneRhoneAlpes,provenceAlpesCoteAzur));

        String legerRouge = "#ffcccb";//"#ACACAC";
        String rouge = "red";

        resetMapColors();


        for(ArrayList<String> region : regions) {
            if (region.contains(departement)) {
                for (String dep : region) {
                    if (dep.equals(departement)){
                        colorizeDepartement(dep, rouge);
                    } else {
                        colorizeDepartement(dep, legerRouge);
                    }
                }
            }
        }
        Thread.sleep(500);
        //setInfosDepartement(departement);

        //colorizeDepartement(departement, rouge);
        return "apprendre-departements";
    }


    public void colorizeRegion(ArrayList<String> region, String color) throws IOException {

    }

    public void colorizeDepartement(String departement, String color) throws IOException {
        String fileName = "src/main/resources/static/img/france_departements.svg";
        Path path = Path.of(fileName);
        String cibleX = "_" + departement + "\"";
        String content = Files.readString(path);
        content = content.replace(cibleX, cibleX + " style=\"fill: " + color + ";\"");
        Files.writeString(path,content);
    }

    public void resetMapColors() throws IOException {
        String fileName = "src/main/resources/static/img/france_departements.svg";
        Path path = Path.of(fileName);
        String regex = "style=\"fill: (#[A-Fa-f0-9]{6}|[a-z]*);\" ";
        String content = Files.readString(path);

        content = content.replaceAll(regex, "");
        Files.writeString(path,content);
    }


}