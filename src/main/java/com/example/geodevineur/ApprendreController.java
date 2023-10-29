package com.example.geodevineur;
import ch.qos.logback.core.joran.action.AppenderRefAction;
import com.example.geodevineur.controllers.DepartementController;
import com.example.geodevineur.controllers.EntityController;
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
    @Autowired
    EntityController entityController;
    @Autowired
    Format format;

    @Setter
    private Model model;

    private final Map map;
    @Getter@Setter
    private String type;

    public ApprendreController(PrefectureController prefectureController_, DepartementController departementController_, RegionController regionController_, EntityController entityController_, Format format_) throws IOException {
        this.model = null;
        this.type = "prefecture";
        this.prefectureController = prefectureController_;
        this.departementController = departementController_;
        this.regionController = regionController_;
        this.entityController = entityController_;
        this.format = format_;
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

        StringBuilder selectContent = getSelectContent(type,name);

        model.addAttribute("selectContent",selectContent);
        model.addAttribute("map",map.getContent());
        model.addAttribute("infos",data);



        return "apprendre";
    }

    @RequestMapping(value = "apprendre")
    public String apprendreMain(Model model) throws IOException {
        System.out.println("dans apprendreMain");
        setType(null);
        map.clear();
        model.addAttribute("selectContent", getSelectContent(type,null));
        model.addAttribute("map",map.getContent());
        return "apprendre";
    }

    @RequestMapping(value = "apprendre", params = "type")
    public String apprendreWithType(Model model, @RequestParam String type) throws IOException {
        System.out.println("dans apprendre with type");
        map.clear();
        switch (type) {
            case "departement", "prefecture", "region":
                setType(type);
                break;
            default:
                System.out.println("laaa avec "+type);
                setType(null);
                break;
        }
        model.addAttribute("selectContent", getSelectContent(type,null));
        model.addAttribute("map",map.getContent());
        return "apprendre";
    }

    @RequestMapping(value = "apprendre", params = "name")
    public String apprendreWithName(Model mode, @RequestParam String name) throws IOException, InterruptedException {
        return apprendre(model, entityController.getTypeByName(name), name);
    }

    public StringBuilder getSelectContent(String type, String name){
        StringBuilder selectContent = new StringBuilder();
        if(departementController.isValidName(name)){
            selectContent.append("<select class=\"custom-select\" value=\"").append(name).append("\" id=\"entity-select\">");
        } else {
            selectContent.append("<select class=\"custom-select\" id=\"entity-select\">");
        }

        System.out.println(type);
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
        htmlContent.append("<p>").append("Préfecture de ").append(format.getLinkOf(prefecture.getDepartement().getName())).append("</p>");
        htmlContent.append("<p>").append("Elle comporte ").append(prefecture.getPopulation()).append(" habitants</p>");

        return htmlContent;
    }

    public StringBuilder getDepartementInfos(Departement departement){
        StringBuilder htmlContent = new StringBuilder();

        htmlContent.append("<h3>").append(departement.getName()).append(" - ").append(departement.getNumber()).append("</h3>");
        htmlContent.append("<p>").append("Département de ").append(format.getLinkOf(departement.getRegion().getName())).append("</p>");
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
}
