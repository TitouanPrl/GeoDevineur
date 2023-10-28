package com.example.geodevineur.controllers;

import com.example.geodevineur.enumerations.Cardinal;
import com.example.geodevineur.enumerations.Politic;
import com.example.geodevineur.repos.RegionRepository;
import com.example.geodevineur.tables.Departement;
import com.example.geodevineur.tables.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

@Controller
public class RegionController {

    @Autowired
    RegionRepository regionService;

    DepartementController departementController;

    public RegionController(DepartementController departementController_){
        this.departementController = departementController_;
    }

    public List<Region> getAll(){
        List<Region> result = new ArrayList<>();
        regionService.findAll().forEach(result::add);
        return result;
    }

    public Region getById(int id){
        return regionService.findById(id).orElse(null);
    }

    /*public Region getByDepartementId(String departement_id){
        return getAll().stream()
                .filter(region -> region.getId().equals(departementController.getById(departement_id).getRegion().getId()))
                .findFirst()
                .orElse(null);
    }*/

    /*public Region getByPrefectureId(String prefecture_id){
        return getByDepartementId(departementController.getByPrefectureId(prefecture_id).getId());
    }*/

    public void add(List<Departement> departements_, String name_, Cardinal card_){
        regionService.save(new Region(departements_, name_, card_));
    }

    public void deleteAll(){
        regionService.deleteAll();
    }
}