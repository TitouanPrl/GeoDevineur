package com.example.geodevineur.controllers;

import com.example.geodevineur.repos.RegionRepository;
import com.example.geodevineur.tables.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class RegionController {

    @Autowired
    RegionRepository regionService;

    DepartementController departementController;

    public RegionController(DepartementController departementController_){
        this.departementController = departementController_;
    }

    /* Returns a list of all regions */
    public List<Region> getAll(){
        List<Region> result = new ArrayList<>();
        regionService.findAll().forEach(result::add);
        Collections.sort(result);
        return result;
    }

    /* Returns a region by its id */
    public Region getById(int id){
        return regionService.findById(id).orElse(null);
    }

    /* Returns a region by its name */
    public Region getByName(String name) {
        Region result = null;
        for(Region region : getAll()){
            if (region.getName().equals(name)){
                result = region;
            }
        }
        return result;
    }

    /* Returns a region by a department id */
    public Region getByDepartementId(int departement_id) {
        return departementController.getById(departement_id).getRegion();
    }

    /* Returns a region by a department name */
    public Region getByDepartementName(String deaprtement_name) {
        return departementController.getByName(deaprtement_name).getRegion();
    }

    /* Checks if the name matches a correct region name */
    public boolean isValidName(String name){
        boolean result = false;
        for(Region region : getAll()){
            if (region.getName().equals(name)) {
                result = true;
                break;
            }
        }
        return result;
    }

    public void add(Region region){
        regionService.save(region);
    }

    public void deleteAll(){
        regionService.deleteAll();
    }
}