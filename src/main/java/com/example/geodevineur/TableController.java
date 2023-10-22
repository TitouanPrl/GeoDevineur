package com.example.geodevineur;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.geodevineur.dep_reg.Departement;
import com.example.geodevineur.enumerations.Cardinal;
import com.example.geodevineur.enumerations.Politic;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;

@Controller
public class TableController {

    @Autowired
    DepartementRepository service;
    
    @PostMapping("/uploadDpt")
    public String uploadDepartements(@RequestParam("file") MultipartFile file) throws Exception {
        List<Departement> departementList = new ArrayList<>();

        /* On importe les CSV de données initiales */
        InputStream inputSream = file.getInputStream();
        CsvParserSettings setting = new CsvParserSettings();
        setting.setHeaderExtractionEnabled(true);
        CsvParser parser = new CsvParser(setting);
        parser.parseAllRecords(inputSream);
        List<com.univocity.parsers.common.record.Record> parseAllRecords = parser.parseAllRecords(inputSream);
       
        /* On convertit chaque entrée en objet */
        parseAllRecords.forEach(record -> {
            Departement departement = new Departement(record.getString("Nom"), Integer.parseInt(record.getString("Habitants")), (double)Float.parseFloat(record.getString("Superficie")), Cardinal.fromString(record.getString("Position")), Boolean.parseBoolean(record.getString("Cotier")), Integer.parseInt(record.getString("Voisins")), record.getString("Numero"), Politic.fromString(record.getString("Politique")));
            departementList.add(departement);
        });

        service.saveAll(departementList);
        return "Upload successfull";
    }
}
