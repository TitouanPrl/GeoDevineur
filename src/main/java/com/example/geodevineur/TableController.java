package com.example.geodevineur;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import com.example.geodevineur.dep_reg.DepReg;
import org.springframework.transaction.annotation.Transactional;
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
    @GetMapping("/uploadData")
    public String uploadDptements(Model model) {
        System.out.println("Uploading on DB ...");

        /* Clearing Database before upload */
        service.deleteAll();

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

    public List<DepReg> getAllDpt() {
        Iterable<DepReg> allDpt = service.findAll();
        List<DepReg> result = new ArrayList<DepReg>();
        allDpt.forEach(depReg -> {
            if (depReg.getType().equals("Departement")) {
                result.add(depReg);
            }
        });
        return result;
    }

    public DepReg getDpt(String id) {
        Iterable<DepReg> allDpt = service.findAll();
        DepReg result = null;
        for(DepReg dep : allDpt){
            if (id.equals(dep.getId())) {
                result = dep;
            }
        }
        return result;
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

}

/* AJOUTER FCT POUR UPLOAD REGIONS */
