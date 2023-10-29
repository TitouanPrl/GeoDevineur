package com.example.geodevineur;
import ch.qos.logback.core.joran.action.AppenderRefAction;
import com.example.geodevineur.controllers.DepartementController;
import com.example.geodevineur.controllers.PrefectureController;
import com.example.geodevineur.controllers.RegionController;
import com.example.geodevineur.tables.Departement;
import com.example.geodevineur.tables.Prefecture;
import com.example.geodevineur.tables.Region;
import jakarta.servlet.annotation.WebServlet;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Controller
public class ApprendreController {

    @Autowired
    PrefectureController prefectureController;

    @Autowired
    DepartementController departementController;

    @Autowired
    RegionController regionController;

    @Setter
    private Model model;

    private final Map map;
    @Getter@Setter
    private String type;

    public ApprendreController(PrefectureController prefectureController_, DepartementController departementController_, RegionController regionController_) throws IOException {
        this.model = null;
        this.type = "prefecture";
        this.prefectureController = prefectureController_;
        this.departementController = departementController_;
        this.regionController = regionController_;
        this.map = new Map("src/main/resources/static/img/france_departements.svg");
    }

    @RequestMapping(value = "apprendre", params = {"type","name"})
    public String apprendre(Model model, @RequestParam String type, @RequestParam String name) throws IOException, InterruptedException {

        String prefectureColor = "#414141";
        String departementColor = "#00561b";
        String regionColor = "#062b16";

        System.out.println(name+"|"+type);
        StringBuilder data = setInfos(type,name);

        map.clear();

        switch (type) {
            case "departement":
                map.colorizeDepartement(departementController.getByName(name), departementColor);
                break;
            case "region":
                map.colorizeRegion(regionController.getByName(name), regionColor);
                break;
            case "prefecture":
                map.colorizeDepartement(departementController.getByPrefectureName(name), prefectureColor);
                break;
            default:
                break;
        }

        StringBuilder selectContent = getSelectContent(type);
        System.out.println(selectContent);

        model.addAttribute("selectContent",selectContent);
        model.addAttribute("map",map.getContent());
        model.addAttribute("infos",data);



        return "apprendre";
    }

    @RequestMapping(value = "apprendre")
    public String apprendreMain(Model model){
        System.out.println("dans apprendreMain");
        model.addAttribute("selectContent", getSelectContent(type));
        model.addAttribute("map",map.getContent());
        return "apprendre";
    }

    @RequestMapping(value = "apprendre", params = "type")
    public String apprendreWithType(Model model, @RequestParam String type){
        System.out.println("dans apprendre with type");
        switch (type) {
            case "departement", "prefecture", "region":
                setType(type);
                break;
            default:
                System.out.println("laaa avec "+type);
                setType(null);
                break;
        }
        model.addAttribute("selectContent", getSelectContent(type));
        model.addAttribute("map",map.getContent());
        return "apprendre";
    }

    public StringBuilder getSelectContent(String type){
        StringBuilder selectContent = new StringBuilder();
        selectContent.append("<select class=\"custom-select\">");
        if(type != null && type.equals("region")){
            for(Region region : regionController.getAll()){
                selectContent.append("<option value=\"").append(region.getName()).append("\">").append(region.getName()).append("</option>");
            }
        } else if(type != null && type.equals("departement")){
            for(Departement departement : departementController.getAll()){
                selectContent.append("<option value=\"").append(departement.getName()).append("\">").append(departement.getName()).append("</option>");
            }
        } else {
            for(Prefecture prefecture : prefectureController.getAll()){
                selectContent.append("<option value=\"").append(prefecture.getName()).append("\">").append(prefecture.getName()).append("</option>");
            }
        }
        selectContent.append("</select>");
        return selectContent;
    }

    @PostMapping("setSelectType")
    public String setSelectType(@RequestParam(name = "type") String type){
        System.out.println("here with "+type);
        switch(type){
            case "prefecture":
                setType(type);
                break;
            case "departement":
                setType(type);
                break;
            case "region":
                setType(type);
                break;
            default:
                setType(null);
        }
        setType(type);
        return "apprendre";
    }

    public StringBuilder setInfos(String type, String name) {
        StringBuilder htmlContent = new StringBuilder();

        switch(type){
            case "prefecture":
                Prefecture prefecture = prefectureController.getByName(name);
                if(prefecture != null){
                    htmlContent = getPrefectureInfos(prefecture);
                }
                break;
            case "departement":
                Departement departement = departementController.getByName(name);
                if(departement != null){
                    htmlContent = getDepartementInfos(departement);
                }
                break;
            case "region":
                Region region = regionController.getByName(name);
                if(region != null){
                    htmlContent = getRegionInfos(region);
                }
                break;
            default:
                break;
        }
        return htmlContent;
    }

    public StringBuilder getPrefectureInfos(Prefecture prefecture){
        StringBuilder htmlContent = new StringBuilder();

        htmlContent.append("<h3>").append(prefecture.getName()).append("</h3>");
        htmlContent.append("<p>").append("Préfecture de ").append(Format.link(prefecture.getDepartement().getName())).append("</p>");
        htmlContent.append("<p>").append("Elle comporte ").append(prefecture.getPopulation()).append(" habitants</p>");

        return htmlContent;
    }

    public StringBuilder getDepartementInfos(Departement departement){
        StringBuilder htmlContent = new StringBuilder();

        htmlContent.append("<h3>").append(departement.getName()).append(" - ").append(departement.getNumber()).append("</h3>");
        htmlContent.append("<p>").append("Département de ").append(Format.link(departement.getRegion().getName())).append("</p>");
        htmlContent.append("<p>").append("Il comporte ").append(Format.intToFormatedString(departement.getPopulation())).append(" habitants");
        htmlContent.append(" pour une superficie de ").append(Format.intToFormatedString((int)departement.getSurface())).append(" km²</p>");
        if(departement.getSeaside()){
            htmlContent.append("<p>Il est cotier</p>");
        } else {
            htmlContent.append("<p>Il n'est pas pas cotier</p>");
        }
        htmlContent.append("<p>Il possède ").append(departement.getNeightbours()).append(" départements voisins</p>");
        htmlContent.append("<p>Il vote ").append(departement.getPolitic().toString()).append(" en majorité</p>");

        return htmlContent;
    }

    public StringBuilder getRegionInfos(Region region){
        StringBuilder htmlContent = new StringBuilder();

        htmlContent.append("<h3>").append(region.getName()).append("</h3>");
        htmlContent.append("<p>").append("Région du ").append(region.getCardinal().toString()).append(" de la France</p>");
        htmlContent.append("<p>").append("Elle comporte ").append(Format.intToFormatedString(region.getPopulation())).append(" habitants</p>");
        htmlContent.append(" pour une superficie de ").append(Format.intToFormatedString(region.getSurface())).append(" km²</p>");

        return htmlContent;
    }

    public String getType(String name){
        String found = null;
        for(Prefecture prefecture : prefectureController.getAll()){
            if (prefecture.getName().equals(name)) {
                found = "prefecture";
                break;
            }
        }
        for(Departement departement : departementController.getAll()){
            if (departement.getName().equals(name)) {
                found = "departement";
                break;
            }
        }
        for(Region region : regionController.getAll()){
            if (region.getName().equals(name)) {
                found = "region";
                break;
            }
        }
        return found;
    }

    public void colorizeDepartement(String departement, String color) throws IOException {
        String fileName = "src/main/resources/static/img/france_departements.svg";
        Path path = Path.of(fileName);
        String cibleX = "_" + departement + "\"";
        String content = Files.readString(path);
        content = content.replace(cibleX, cibleX + " style=\"fill: " + color + ";\"");
        Files.writeString(path,content);
    }

    public void resetMap() throws IOException {
        String fileName = "src/main/resources/static/img/france_departements.svg";
        Path path = Path.of(fileName);
        String regex = "style=\"fill: (#[A-Fa-f0-9]{6}|[a-z]*);\" ";
        String content = Files.readString(path);

        content = content.replaceAll(regex, "");
        Files.writeString(path,content);
    }



    //----------------------------------------------------

    @GetMapping("apprendre-regions")
    public String apprendreRegions(Model model) throws IOException, InterruptedException {

        ArrayList<String> allRegions = new ArrayList<>(Arrays.asList("Ile-de-France","Nouvelle-Aquitaine","Corse","Bretagne","Hauts-de-France","Occitanie","Normandie","Grand Est","Pays de la Loire","Centre-Val de Loire","Bourgogne-Franche-Comté","Auvergne-Rhône-Alpes","Provence-Alpes-Côte d'Azur"));
        ArrayList<String> allIDs = new ArrayList<String>(Arrays.asList("ileDeFrance","nouvelleAquitaine","corse","bretagne","hautsDeFrance","occitanie","normandie","grandEst","paysDeLaLoire","centreValDeLoire","bourgogneFrancheComte","auvergneRhoneAlpes","provenceAlpesCoteAzur"));

        Collections.sort(allRegions);
        Collections.sort(allIDs);

        StringBuilder selectContent = new StringBuilder();
        selectContent.append("<select class=\"custom-select\" name=\"regions\" id=\"region-select\">");
        int i=0;
        for (String nom : allRegions) {
            selectContent.append("<option value=\"").append(allIDs.get(i)).append("\">").append(nom).append("</option>");
            i++;
        }
        selectContent.append("</select>");
        model.addAttribute("selectContent", selectContent);
        model.addAttribute("allRegions", allRegions);
        return "apprendre-regions"; // renvoie le nom de votre fichier HTML
    }

}
