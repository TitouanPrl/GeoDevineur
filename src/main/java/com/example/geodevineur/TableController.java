package com.example.geodevineur;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.geodevineur.dep_reg.Departement;
import com.example.geodevineur.enumerations.Cardinal;
import com.example.geodevineur.enumerations.Politic;

import static java.lang.Math.round;


@Controller
public class TableController {

    @Autowired
    DeRegRepository service;

    /* Convertis et injecte le csv département dans la BDD */
    @GetMapping("/uploadDpt")
    public String uploadDptements(Model model) {
        System.out.println("YESSSAI MONGARS");

        List<Departement> departementList = new ArrayList<Departement>();
        String filePath = "src/main/resources/static/csv/departements.csv";

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            line = br.readLine(); /* On saute la première ligne */
            while ((line = br.readLine()) != null) {
                /* On crée un tableau en utilisant le séparateur pour séparer les cases */
                String[] values = line.split(";");
                /* On accède aux éléments via le tableau */
                Departement departement = new Departement   (
                        values[1], //Nom
                        values[2], //Numero
                        Integer.parseInt(values[3]), //Population
                        round((double) Float.parseFloat(values[4]),2), //Superficie
                        Cardinal.fromString(values[5]), //Position
                        IntToBoolean(Integer.parseInt(values[6])), //Cotier
                        Integer.parseInt(values[7]), //Voisins
                        Politic.fromString(values[8])); //Politique

                /* On ajoute l'élément à la liste */
                departementList.add(departement);

            }
        }catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("status","failed");
            return "bdd";
        }
        service.saveAll(departementList);
        model.addAttribute("status","success");
        return "bdd";
    }

    @GetMapping("/test")
    public String test() {
        System.out.println("nice");
        return "regles";
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    public static Boolean IntToBoolean(int x){
        return (x == 1);
    }

    // /* On importe les CSV de données initiales */
    // BufferedReader reader = new BufferedReader(new FileReader("../../../../ressources/static/csv/departements.csv"));
    // CsvParserSettings setting = new CsvParserSettings();
    // setting.setHeaderExtractionEnabled(true);
    // CsvParser parser = new CsvParser(setting);
    // parser.parseAllRecords(reader);
    // List<com.univocity.parsers.common.record.Record> parseAllRecords = parser.parseAllRecords(reader);

    // /* On convertit chaque entrée en objet */
    // parseAllRecords.forEach(record -> {
    //     Departement departement = new Departement(record.getString("Nom"),
    //         Integer.parseInt(record.getString("Habitants")),
    //         (double)Float.parseFloat(record.getString("Superficie")),
    //         Cardinal.fromString(record.getString("Position")),
    //         Boolean.parseBoolean(record.getString("Cotier")),
    //         Integer.parseInt(record.getString("Voisins")),
    //         record.getString("Numero"),
    //         Politic.fromString(record.getString("Politique")));
    //         departementList.add(departement);
    // });

    //
}

/* AJOUTER FCT POUR UPLOAD REGIONS */
