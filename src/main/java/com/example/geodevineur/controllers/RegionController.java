package com.example.geodevineur.controllers;

import com.example.geodevineur.enumerations.Cardinal;
import com.example.geodevineur.enumerations.Politic;
import com.example.geodevineur.repos.RegionRepository;
import com.example.geodevineur.tables.Departement;
import com.example.geodevineur.tables.Prefecture;
import com.example.geodevineur.tables.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
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

    public Region getByName(String name) {
        Region result = null;
        for(Region region : getAll()){
            if (region.getName().equals(name)){
                result = region;
            }
        }
        return result;
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

    public void add(Region region){
        regionService.save(region);
    }

    public void deleteAll(){
        regionService.deleteAll();
    }
}