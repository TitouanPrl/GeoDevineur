package com.example.geodevineur;

import java.util.Random;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.geodevineur.condition.CardinalCond;
import com.example.geodevineur.condition.Condition;
import com.example.geodevineur.condition.ContainLetterCond;
import com.example.geodevineur.condition.NeighbourCond;
import com.example.geodevineur.condition.PoliticCond;
import com.example.geodevineur.condition.PrefectureCond;
import com.example.geodevineur.condition.RegionCond;
import com.example.geodevineur.condition.SeasideCond;
import com.example.geodevineur.controllers.DepartementController;
import com.example.geodevineur.tables.Departement;
import com.example.geodevineur.tables.Region;
import com.example.geodevineur.enumerations.Cardinal;
import com.example.geodevineur.enumerations.Politic;
import java.util.List;

@SpringBootApplication
public class GeoDevineurApplication {

    public static void main(String[] args) {

        SpringApplication.run(GeoDevineurApplication.class, args);
    }

}