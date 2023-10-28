package com.example.geodevineur.controllers;

import com.example.geodevineur.enumerations.Politic;
import com.example.geodevineur.repos.DepartementRepository;
import com.example.geodevineur.tables.Departement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
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

    public Departement getByNumber(String number){
        Departement result = null;
        for(Departement departement : getAll()){
            if (departement.getNumber().equals(number)){
                result = departement;
            }
        }
        return result;
    }

    public Departement getByPrefectureId(int prefecture_id){
        Departement result = null;
        for(Departement departement : getAll()){
            if (departement.getPrefecture().getId() == prefecture_id){
                result = departement;
            }
        }
        return result;
    }

    public Departement getByPrefectureName(String prefecture_name){
        Departement result = null;
        for(Departement departement : getAll()){
            if (departement.getPrefecture().getName().equals(prefecture_name)){
                result = departement;
            }
        }
        return result;
    }

    /*public List<Departement> getByRegionId(int region_id){
        List<Departement> result = new ArrayList<>();
        for(Departement departement : getAll()){
            if (departement.getRegion().getId() == region_id){
                result.add(departement);
            }
        }
        return result;
    }*/

    public void add(Departement departement){
        departementService.save(departement);
    }

    public void deleteAll(){
        departementService.deleteAll();
    }
}