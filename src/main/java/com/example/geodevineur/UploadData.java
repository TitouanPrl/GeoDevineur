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

    //Procedure servant à vider la base de donnée des pref/dep/ref
    public void deleteAll(){
        regionController.deleteAll();
        prefectureController.deleteAll();
        departementController.deleteAll();
    }

    //Type temporaire contenant une prefecture et son departement (en string) pour l'insere aisement dans la bdd
    //va etre fusionné avec l'autre temporaire
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

    //Procedure servant à créer le lien entre un departement et une prefecture simultannément
    @Transactional
    public void saveOneToOne(Prefecture prefecture, Departement departement){
        prefecture.setDepartement(departement);
        departement.setPrefecture(prefecture);
    }
    //Procedure servant à créer le lien entre une region et ses departement simultannément
    @Transactional
    public void saveManyToOne(List<Departement> allDpts, Region region){
        region.setDepartements(allDpts);
        allDpts.forEach(departement -> departement.setRegion(region));
    }

    //Fonction qui recupere la liste de toutes les regions contenues dans le csv regions.csv
    public List<Region> readRegionsCSV(){
        List<Region> allRegions = new ArrayList<>();
        String filePathRgs = "src/main/resources/static/csv/regions.csv";

        try (BufferedReader brP = new BufferedReader(new FileReader(filePathRgs))) {
            String line;
            line = brP.readLine(); /* On saute la première ligne */
            while ((line = brP.readLine()) != null) {
                /* On crée un tableau en utilisant le séparateur pour séparer les cases */
                String[] values = line.split(",");
                /* On accède aux éléments via le tableau */
                Region region = new Region(
                        values[1], //Nom
                        Cardinal.fromString(values[2])); //Cardinal

                /* On ajoute l'élément à la liste */
                allRegions.add(region);
                //System.out.println(values[1]+"|"+values[2]+"|"+values[3]);
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
        return allRegions;
    }

    //Fonction qui recupere la liste de tous les departements contenues dans le csv departements.csv
    public List<DepartementBis<Departement,String>> readDepartementsCSV(){
        List<DepartementBis<Departement,String>> allDepartements = new ArrayList<>();
        String filePathDpts = "src/main/resources/static/csv/departements.csv";

        try (BufferedReader brP = new BufferedReader(new FileReader(filePathDpts))) {
            String line;
            line = brP.readLine(); /* On saute la première ligne */
            while ((line = brP.readLine()) != null) {
                /* On crée un tableau en utilisant le séparateur pour séparer les cases */
                String[] values = line.split(",");
                /* On accède aux éléments via le tableau */
                Departement departement = new Departement(
                        values[2], //Nom
                        values[3], //Number
                        Integer.parseInt(values[4]), //Population
                        Format.round((double) Float.parseFloat(values[5]),2), //Superficie
                        Format.IntToBoolean(Integer.parseInt(values[7])), //Cotier
                        Integer.parseInt(values[8]), //Voisins
                        Politic.fromString(values[9])); //Politique
                //Cardinal pas utilisé
                String regionName = values[1];

                /* On ajoute l'élément à la liste */
                allDepartements.add(new DepartementBis<>(departement,regionName));
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
        return allDepartements;
    }

    //Fonction qui recupere la liste de toutes les prefectures contenues dans le csv prefectures.csv
    public List<PrefectureBis<Prefecture,String>> readPrefecturesCSV(){
        List<PrefectureBis<Prefecture,String>> allPrefectures = new ArrayList<>();
        String filePathPrfs = "src/main/resources/static/csv/prefectures.csv";

        try (BufferedReader brP = new BufferedReader(new FileReader(filePathPrfs))) {
            String lineP;
            lineP = brP.readLine(); /* On saute la première ligne */
            while ((lineP = brP.readLine()) != null) {
                /* On crée un tableau en utilisant le séparateur pour séparer les cases */
                String[] values = lineP.split(",");
                /* On accède aux éléments via le tableau */
                Prefecture prefecture = new Prefecture(
                        values[1], //Nom
                        Integer.parseInt(values[3])); //Population

                String departementName = values[2];

                /* On ajoute l'élément à la liste */
                allPrefectures.add(new PrefectureBis<>(prefecture,departementName));
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
        return allPrefectures;
    }

    //Fonction principale qui vide et remplis la BDD des departements/regions/prefectures depuis les csv
    @GetMapping("upload")
    public String upload(Model model){

        deleteAll();

        List<Region> allRegions = readRegionsCSV();
        List<DepartementBis<Departement,String>> allDepartementsBis = readDepartementsCSV();
        List<PrefectureBis<Prefecture,String>> allPrefecturesBis = readPrefecturesCSV();

        for(Region region : allRegions){
            //On veut recup tout les departements de la region
            List<Departement> departementsOfRegion = new ArrayList<>();

            //On boucle sur tous les departements du csv
            for(DepartementBis<Departement,String> depBis : allDepartementsBis){
                //Si la region du departement du csv est la bonne
                if(depBis.regionName.equals(region.getName())){
                    departementsOfRegion.add(depBis.departement);
                    //Et de la meme maniere on recupere la prefecture de ce departement
                    for(PrefectureBis<Prefecture,String> prefBis : allPrefecturesBis){
                        if(prefBis.departementName.equals(depBis.departement.getName())){
                            //On crée le lien entre chaque departement et sa prefecture
                            saveOneToOne(prefBis.prefecture,depBis.departement);
                        }
                    }
                }
            }
            //On crée le lien entre les departements et leur region
            saveManyToOne(departementsOfRegion,region);
            regionController.add(region);
        }
        scoreController.add(new Score("toto", "", 100));

        //-------------------------------------------------------------------------

        model.addAttribute("status","success");
        return "bdd";
    }
}
