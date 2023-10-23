package com.example.geodevineur;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.geodevineur.dep_reg.Departement;
import com.example.geodevineur.enumerations.Cardinal;
import com.example.geodevineur.enumerations.Politic;

import ch.qos.logback.core.model.Model;

@Controller
public class TableController {

    @Autowired
    DepartementRepository service;
    
    @PostMapping(path = "/uploadDpt")
    public String uploadDepartements(Model model) throws Exception {
        System.out.println("YESSSAI MONGARS");
        List<Departement> departementList = new ArrayList<Departement>();
        String filePath = "../../../../ressources/static/csv/departements.csv";

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            line = br.readLine(); /* On saute la première ligne */
            while ((line = br.readLine()) != null) {
                /* On crée un tableau en utilisant le séparateur pour séparer les cases */
                String[] values = line.split(",");
                                
                /* On accède aux éléments via le tableau */
                Departement departement = new Departement(values[0], 
                                                         Integer.parseInt(values[1]), 
                                                         (double)Float.parseFloat(values[2]), 
                                                         Cardinal.fromString(values[3]), 
                                                         Boolean.parseBoolean(values[4]), 
                                                         Integer.parseInt(values[5]), 
                                                         values[6], 
                                                         Politic.fromString(values[7]));

                /* On ajoute l'élément à la liste */
                departementList.add(departement);
                
                service.saveAll(departementList);
                return "Upload successfull";
            }
        }catch (IOException e) {
            e.printStackTrace();
            return "Upload bug";
        }
        return "Upload en demi teinte";
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

