package com.example.geodevineur.controllers;

import com.example.geodevineur.repos.PrefectureRepository;
import com.example.geodevineur.tables.Departement;
import com.example.geodevineur.tables.Prefecture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
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

//    public List<Prefecture> getAllByRegionId(int region_id){
//        List<Prefecture> result = new ArrayList<>();
//        for(Departement departement : getAllByRegionId(region_id)){
//            result.add(departement.getPrefecture());
//        }
//        return result;
//    }

    public void add(Prefecture prefecture){
        prefectureService.save(prefecture);
    }

    public void deleteAll(){
        prefectureService.deleteAll();
    }
}