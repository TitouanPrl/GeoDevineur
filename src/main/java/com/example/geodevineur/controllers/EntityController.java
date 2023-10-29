package com.example.geodevineur.controllers;

import com.example.geodevineur.tables.Prefecture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

@Component
public class EntityController {

    @Autowired
    RegionController regionController;
    @Autowired
    DepartementController departementController;
    @Autowired
    PrefectureController prefectureController;

    public EntityController(RegionController regionController_, DepartementController departementController_, PrefectureController prefectureController_){
        this.regionController = regionController_;
        this.departementController = departementController_;
        this.prefectureController = prefectureController_;
    }

    public String getTypeByName(String name){
        String type = null;
        if(regionController.isValidName(name)){
            type = "region";
        } else if(departementController.isValidName(name)){
            type = "departement";
        } else if(prefectureController.isValidName(name)){
            type = "prefecture";
        }
        return type;
    }


}
