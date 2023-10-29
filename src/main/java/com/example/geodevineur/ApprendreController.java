package com.example.geodevineur;
import ch.qos.logback.core.joran.action.AppenderRefAction;
import com.example.geodevineur.controllers.DepartementController;
import com.example.geodevineur.controllers.PrefectureController;
import com.example.geodevineur.controllers.RegionController;
import com.example.geodevineur.tables.Departement;
import com.example.geodevineur.tables.Prefecture;
import com.example.geodevineur.tables.Region;
import org.springframework.beans.factory.annotation.Autowired;
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

    private Model model;

    public ApprendreController(PrefectureController prefectureController_, DepartementController departementController_, RegionController regionController_) {
        this.model = null;
        this.prefectureController = prefectureController_;
        this.departementController = departementController_;
        this.regionController = regionController_;
    }

    public void setModel(Model model){
        this.model = model;
    }

    @GetMapping("apprendre")
    public String apprendre(Model model, @RequestParam(name = "nom", defaultValue = "null") String name) throws IOException, InterruptedException {
        resetMap();

        String type = getType(name);
        System.out.println(name+"|"+type);
        StringBuilder data = setInfos(type,name);
        model.addAttribute("infos",data);

        return "apprendre";

/*

        resetMapDepartementsColors();
        Thread.sleep(500);

        //Get les departements dans la bdd
        //List<Departement> allDepartements = tableController.getAllDpt();

        StringBuilder selectContent = new StringBuilder();
        selectContent.append("<select class=\"custom-select\" name=\"departements\" id=\"departement-select\">");
        int i=0;
        for (Departement depReg : allDepartements) {
            selectContent.append("<option value=\"").append(depReg.getId()).append("\">").append(depReg.getId()).append(" - ").append(depReg.getName()).append("</option>");
            i++;
        }
        selectContent.append("</select>");
        model.addAttribute("selectContent", selectContent);
        model.addAttribute("allDepartements", allDepartements);
        model.addAttribute("superficie","7500");
        model.addAttribute("habitants","420 000");
        model.addAttribute("position","l'Est");
        model.addAttribute("cotier","se trouve dans les terres");
        model.addAttribute("voisins","6");
        model.addAttribute("politique","RN");
*/

        //return "apprendre";
    }
    @PostMapping("setDepartement")
    public String setDepartement(String departement) throws IOException, InterruptedException {
        System.out.println(departement);
        if (departement.length() == 1){
            departement = "0" + departement;
        }
        System.out.println("here with "+departement);

        ArrayList<String> nouvelleAquitaine = new ArrayList<String>(Arrays.asList("16","17","19","23","24","33","40","47","64","79","86","87"));
        ArrayList<String> bretagne = new ArrayList<String>(Arrays.asList("22","29","35","56"));
        ArrayList<String> hautsDeFrance = new ArrayList<String>(Arrays.asList("02","59","60","62","80"));
        ArrayList<String> occitanie = new ArrayList<String>(Arrays.asList("09","11","12","30","31","32","34","46","48","65","66","81","82"));
        ArrayList<String> normandie = new ArrayList<String>(Arrays.asList("14","27","50","61","76"));
        ArrayList<String> ileDeFrance = new ArrayList<String>(Arrays.asList("75","92","93","94","77","78","91","95"));
        ArrayList<String> grandEst = new ArrayList<String>(Arrays.asList("08","10","51","52","54","55","57","67","68","88"));
        ArrayList<String> paysDeLaLoire = new ArrayList<String>(Arrays.asList("44","49","53","72","85"));
        ArrayList<String> centreValDeLoire = new ArrayList<String>(Arrays.asList("18","28","36","37","41","45"));
        ArrayList<String> bourgogneFrancheComte = new ArrayList<String>(Arrays.asList("21","25","39","58","70","71","89","90"));
        ArrayList<String> auvergneRhoneAlpes = new ArrayList<String>(Arrays.asList("01","03","07","15","26","38","42","43","63","69","73","74"));
        ArrayList<String> provenceAlpesCoteAzur = new ArrayList<String>(Arrays.asList("04","05","06","13","83","84"));
        ArrayList<String> corse = new ArrayList<String>(Arrays.asList("2a","2b"));

        ArrayList<ArrayList<String>> regions = new ArrayList<ArrayList<String>>(Arrays.asList(ileDeFrance,nouvelleAquitaine,corse,bretagne,hautsDeFrance,occitanie,normandie,grandEst,paysDeLaLoire,centreValDeLoire,bourgogneFrancheComte,auvergneRhoneAlpes,provenceAlpesCoteAzur));

        String legerRouge = "#ffcccb";//"#ACACAC";
        String rouge = "red";

        resetMap();


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

        htmlContent.append("<h3>").append(departement.getName()).append("</h3>");
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
