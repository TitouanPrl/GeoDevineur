package com.example.geodevineur.couzi;

import com.example.geodevineur.enumerations.Politic;
import com.example.geodevineur.tables.Departement;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConditionController {

    public List<Condition> getAllCondsOfDep(Departement departement){
        List<Condition> allConds = new ArrayList<>();
        List<Integer> allDeltas = new ArrayList<>(Arrays.asList(-50,-25,-10,5,5,10,25,50));
        List<String> allAttributs = new ArrayList<>(Arrays.asList("population","surface","seaside","neightbours","politic"));

        for(String attribut : allAttributs){
            if(attribut.equals("population") || attribut.equals("surface") || attribut.equals("neightbours")){
                for(int delta : allDeltas){
                    allConds.add(new Condition(delta,attribut,departement));
                }
            } else if(attribut.equals("seaside") || attribut.equals("politic")){
                allConds.add(new Condition(0,attribut,departement));
            }
        }
        return allConds;
    }

    public void printAllCondsOfDep(Departement departement){
        List<Condition> allConds = getAllCondsOfDep(departement);
        for(Condition condition : allConds){
            System.out.println(condition.getSentence());
        }
    }
}
