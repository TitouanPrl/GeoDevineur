package com.example.geodevineur;

import com.example.geodevineur.controllers.DepartementController;
import com.example.geodevineur.controllers.PrefectureController;
import com.example.geodevineur.controllers.RegionController;
import com.example.geodevineur.controllers.ScoreController;
import com.example.geodevineur.enumerations.Cardinal;
import com.example.geodevineur.enumerations.Politic;
import com.example.geodevineur.tables.Departement;
import com.example.geodevineur.tables.Prefecture;
import com.example.geodevineur.tables.Region;
import com.example.geodevineur.tables.Score;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UploadData {

    @Autowired
    RegionController regionController;
    @Autowired
    DepartementController departementController;
    @Autowired
    PrefectureController prefectureController;
    @Autowired
    ScoreController scoreController;

    public UploadData(RegionController regionController_, DepartementController departementController_, PrefectureController prefectureController_, ScoreController scoreController_){
        this.regionController = regionController_;
        this.departementController = departementController_;
        this.prefectureController = prefectureController_;
        this.scoreController = scoreController_;
    }

    /*Clear the db */
    public void deleteAll(){
        regionController.deleteAll();
        prefectureController.deleteAll();
        departementController.deleteAll();
    }

    //Type temporaire contenant une prefecture et son departement (en string) pour l'insere aisement dans la bdd
    //va etre fusionné avec l'autre temporaire
    /* Tmp class to store string data and easily insert them into the db */
    public class PrefectureBis<Prefecture, String> {
        Prefecture prefecture;
        String departementName;

        public PrefectureBis(Prefecture pref, String name){
            this.prefecture = pref;
            this.departementName = name;
        }
    }

    //Type temporaire contenant un departement et sa region (en string) pour l'insere aisement dans la bdd
    //va etre fusionné avec l'autre temporaire
    public class DepartementBis<Departement, String> {
        Departement departement;
        String regionName;

        public DepartementBis(Departement dep, String name){
            this.departement = dep;
            this.regionName = name;
        }
    }

    /* Creates a link between a department and its prefecture */
    @Transactional
    public void saveOneToOne(Prefecture prefecture, Departement departement){
        prefecture.setDepartement(departement);
        departement.setPrefecture(prefecture);
    }
    /* Creates a link between a region and its departments */
    @Transactional
    public void saveManyToOne(List<Departement> allDpts, Region region){
        region.setDepartements(allDpts);
        allDpts.forEach(departement -> departement.setRegion(region));
    }

    /* Gets the list of regions from the csv */
    public List<Region> readRegionsCSV(){
        List<Region> allRegions = new ArrayList<>();
        String filePathRgs = "src/main/resources/static/csv/regions.csv";

        try (BufferedReader brP = new BufferedReader(new FileReader(filePathRgs))) {
            String line;
            line = brP.readLine(); /* Jumping the header line */
            while ((line = brP.readLine()) != null) {
                /* Creating an array using the separator to set the cases */
                String[] values = line.split(",");
                /* Accessing the elements throught the array */
                Region region = new Region(
                        values[1], /* Name */
                        Cardinal.fromString(values[2])); /* Cardinal */

                /* Adding element to the list */
                allRegions.add(region);
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
        return allRegions;
    }

    /* Gets the list of departments from the csv */
    public List<DepartementBis<Departement,String>> readDepartementsCSV(){
        List<DepartementBis<Departement,String>> allDepartements = new ArrayList<>();
        String filePathDpts = "src/main/resources/static/csv/departements.csv";

        try (BufferedReader brP = new BufferedReader(new FileReader(filePathDpts))) {
            String line;
            line = brP.readLine(); 
            while ((line = brP.readLine()) != null) {
                String[] values = line.split(",");
                Departement departement = new Departement(
                        values[2], /* Name */
                        values[3], /* Number of the department */
                        Integer.parseInt(values[4]), /* Population */
                        Format.round((double) Float.parseFloat(values[5]),2), /* Surface */
                        Format.IntToBoolean(Integer.parseInt(values[7])), /* Seaside */
                        Integer.parseInt(values[8]), /* Neighbours */
                        Politic.fromString(values[9])); /* Politic */
                String regionName = values[1];

                /* Adding element to the list */
                allDepartements.add(new DepartementBis<>(departement,regionName));
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
        return allDepartements;
    }

    /* Gets the list of prefectures from the csv */
    public List<PrefectureBis<Prefecture,String>> readPrefecturesCSV(){
        List<PrefectureBis<Prefecture,String>> allPrefectures = new ArrayList<>();
        String filePathPrfs = "src/main/resources/static/csv/prefectures.csv";

        try (BufferedReader brP = new BufferedReader(new FileReader(filePathPrfs))) {
            String lineP;
            lineP = brP.readLine(); 
            while ((lineP = brP.readLine()) != null) {
                String[] values = lineP.split(",");
                Prefecture prefecture = new Prefecture(
                        values[1], /* Names */
                        Integer.parseInt(values[3])); /* Population */

                String departementName = values[2];

                /* Adding element to the list */
                allPrefectures.add(new PrefectureBis<>(prefecture,departementName));
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
        return allPrefectures;
    }

    /* Main fonction, clear and fill up the db from the csv */
    @GetMapping("upload")
    public String upload(Model model){

        deleteAll();

        List<Region> allRegions = readRegionsCSV();
        List<DepartementBis<Departement,String>> allDepartementsBis = readDepartementsCSV();
        List<PrefectureBis<Prefecture,String>> allPrefecturesBis = readPrefecturesCSV();

        /* We get all the departments of the region */
        for(Region region : allRegions){
            List<Departement> departementsOfRegion = new ArrayList<>();

            for(DepartementBis<Departement,String> depBis : allDepartementsBis){
                if(depBis.regionName.equals(region.getName())){
                    departementsOfRegion.add(depBis.departement);
                    for(PrefectureBis<Prefecture,String> prefBis : allPrefecturesBis){
                        if(prefBis.departementName.equals(depBis.departement.getName())){
                            /* Creating the link between department and prefecture */
                            saveOneToOne(prefBis.prefecture,depBis.departement);
                        }
                    }
                }
            }
            /* Creating the link between department and region */
            saveManyToOne(departementsOfRegion,region);
            regionController.add(region);
        }
        scoreController.add(new Score("toto", "", 100));

        //-------------------------------------------------------------------------

        model.addAttribute("status","success");
        return "bdd";
    }
}
