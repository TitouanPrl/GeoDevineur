package com.example.geodevineur.controller;

import com.example.geodevineur.repos.PrefectureRepository;
import com.example.geodevineur.tables.Prefecture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class PrefectureController {

    @Autowired
    PrefectureRepository prefectureService;

    public List<Prefecture> getAll(){
        List<Prefecture> result = new ArrayList<>();
        prefectureService.findAll().forEach(result::add);
        Collections.sort(result);
        return result;
    }

    public Prefecture getById(int id){
        return prefectureService.findById(id).orElse(null);
    }

    public Prefecture getByName(String name) {
        Prefecture result = null;
        for(Prefecture prefecture : getAll()){
            if (prefecture.getName().equals(name)){
                result = prefecture;
            }
        }
        return result;
    }
    public Prefecture getByDepartementId(int departement_id){
        Prefecture result = null;
        for(Prefecture prefecture : getAll()){
            if (prefecture.getDepartement().getId() == departement_id){
                result = prefecture;
            }
        }
        return result;
    }



    public Prefecture getByDepartementName(String departement_name){
        Prefecture result = null;
        for(Prefecture prefecture : getAll()){
            if (prefecture.getDepartement().getName().equals(departement_name)){
                result = prefecture;
            }
        }
        return result;
    }

    public Prefecture getByDepartementNumber(String departement_number){
        Prefecture result = null;
        for(Prefecture prefecture : getAll()){
            if (prefecture.getDepartement().getNumber().equals(departement_number)){
                result = prefecture;
            }
        }
        return result;
    }

    public boolean isValidName(String name){
        boolean result = false;
        for(Prefecture prefecture : getAll()){
            if (prefecture.getName().equals(name)) {
                result = true;
                break;
            }
        }
        return result;
    }

    public void add(Prefecture prefecture){
        prefectureService.save(prefecture);
    }

    public void deleteAll(){
        prefectureService.deleteAll();
    }
}