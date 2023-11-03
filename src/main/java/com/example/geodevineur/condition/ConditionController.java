package com.example.geodevineur.condition;

import com.example.geodevineur.controller.DepartementController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConditionController {

    @Autowired
    private DepartementController departementController;
}
