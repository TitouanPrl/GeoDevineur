package com.example.geodevineur.controllers;

import com.example.geodevineur.repos.PrefectureRepository;
import com.example.geodevineur.tables.Prefecture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PrefectureController {

    @Autowired
    PrefectureRepository prefectureService;

    public List<Prefecture> getAll(){
        List<Prefecture> result = new ArrayList<>();
        prefectureService.findAll().forEach(result::add);
        return result;
    }

    public Prefecture getById(int id){
        return prefectureService.findById(id).orElse(null);
    }

    public Prefecture getByDepartementId(String departement_id){
        return getAll().stream()
                .filter(prefecture -> prefecture.getDepartement().getId().equals(departement_id))
                .findFirst()
                .orElse(null);
    }

    public List<Prefecture> getByRegionId(int region_id){
        List<Prefecture> result = new ArrayList<>();
        for(Prefecture prefecture : getAll()){
            if (prefecture.getDepartement().getRegion().getId() == region_id){
                result.add(prefecture);
            }
        }
        return result;
    }

    public void add(String name, int population){
        prefectureService.save(new Prefecture(name,population));
    }

    public void deleteAll(){
        prefectureService.deleteAll();
    }
}