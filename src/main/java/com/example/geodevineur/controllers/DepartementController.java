package com.example.geodevineur.controllers;

import com.example.geodevineur.enumerations.Politic;
import com.example.geodevineur.repos.DepartementRepository;
import com.example.geodevineur.tables.Departement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Service
public class DepartementController {

    @Autowired
    DepartementRepository departementService;

    public List<Departement> getAll(){
        List<Departement> result = new ArrayList<>();
        departementService.findAll().forEach(result::add);
        Collections.sort(result);
        return result;
    }

    //Retourne departement au hasard
    public Departement getRandomOne(){
        return getAll().get(new Random().nextInt(95));
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

    public Departement getByName(String name){
        Departement result = null;
        for(Departement departement : getAll()){
            if (departement.getName().equals(name)){
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

    public List<Departement> getByRegionId(int region_id){
        List<Departement> result = new ArrayList<>();
        for(Departement departement : getAll()){
            if (departement.getRegion().getId() == region_id){
                result.add(departement);
            }
        }
        return result;
    }

    public List<Departement> getByRegionName(String region_name){
        List<Departement> result = new ArrayList<>();
        for(Departement departement : getAll()){
            if (departement.getRegion().getName().equals(region_name)){
                result.add(departement);
            }
        }
        return result;
    }

    public boolean isValidName(String name){
        boolean result = false;
        for(Departement departement : getAll()){
            if (departement.getName().equals(name)) {
                result = true;
                break;
            }
        }
        return result;
    }

    public void add(Departement departement){
        departementService.save(departement);
    }

    public void deleteAll(){
        departementService.deleteAll();
    }
}