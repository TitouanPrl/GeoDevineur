package com.example.geodevineur.controllers;

import com.example.geodevineur.enumerations.Politic;
import com.example.geodevineur.repos.DepartementRepository;
import com.example.geodevineur.tables.Departement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

@Controller
public class DepartementController {

    @Autowired
    DepartementRepository departementService;

    public List<Departement> getAll(){
        List<Departement> result = new ArrayList<>();
        departementService.findAll().forEach(result::add);
        return result;
    }

    public Departement getById(int id){
        return departementService.findById(id).orElse(null);
    }

    public Departement getByPrefectureId(String prefecture_id){
        return getAll().stream()
                .filter(departement -> departement.getId().equals(prefecture_id))
                .findFirst()
                .orElse(null);
    }

    public List<Departement> getByRegionId(int region_id){
        List<Departement> result = new ArrayList<>();
        for(Departement departement : getAll()){
            if (departement.getRegion().getId() == region_id){
                result.add(departement);
            }
        }
        return result;
    }

    public void add(String name_, String id_, int pop_, double surf_, boolean sea_, int neigh_, Politic politic_){
        departementService.save(new Departement(name_, id_, pop_, surf_, sea_, neigh_, politic_));
    }

    public void deleteAll(){
        departementService.deleteAll();
    }
}