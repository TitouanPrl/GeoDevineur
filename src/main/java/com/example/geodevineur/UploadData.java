package com.example.geodevineur;


import com.example.geodevineur.enumerations.Cardinal;
import com.example.geodevineur.enumerations.Politic;
import com.example.geodevineur.repos.DepartementRepository;
import com.example.geodevineur.repos.PrefectureRepository;
import com.example.geodevineur.repos.RegionRepository;
import com.example.geodevineur.tables.Departement;
import com.example.geodevineur.tables.Prefecture;
import com.example.geodevineur.tables.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UploadData {

    @Autowired
    RegionRepository regionRepository;

    @Autowired
    DepartementRepository departementRepository;

    @Autowired
    PrefectureRepository prefectureRepository;

    @GetMapping("upload")
    public String upload(Model model){

        List<Departement> allDpts = new ArrayList<>();

        Prefecture pref1 = new Prefecture();
        pref1.setName("Pau");
        pref1.setPopulation(2000);
        Prefecture pref2 = new Prefecture("Bordeaux", 5000);
        pref2.setName("Bordeaux");
        pref1.setPopulation(50000);

        allDpts.add(new Departement(pref1,"Pyrenees-Atl","64",10000,750.8,false,3, Politic.LR));
        allDpts.add(new Departement(pref2,"Gironde","31",50000,4800.5,true,4,Politic.RE));

        Region reg1 = new Region(allDpts,"Nouvelle-Aquitaine", Cardinal.SW);

        regionRepository.save(reg1);
        model.addAttribute("status","success");
        return "bdd";
    }
}
