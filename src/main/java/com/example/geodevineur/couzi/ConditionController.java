/*
package com.example.geodevineur.couzi;

import com.example.geodevineur.enumerations.Politic;
import com.example.geodevineur.tables.Departement;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ConditionController {

    public List<Condition> getAllCondsOfDep(Departement departement){
        List<Condition> allConds = new ArrayList<>();
        List<String> allAttributs = new ArrayList<>(Arrays.asList("population","surface","seaside","neightbours","politic"));

        for(String attribut : allAttributs){
            if(attribut.equals("population") || attribut.equals("surface") || attribut.equals("neightbours")){
                if(attribut.equals("neightbours")){
                    for(int delta : Arrays.asList(-50,-25,-10,10,25,50)){
                        allConds.add(new Condition(delta,attribut,departement));
                    }
                } else {
                    for(int delta : Arrays.asList(-50,-25,-10,-5,5,10,25,50)){
                        allConds.add(new Condition(delta,attribut,departement));
                    }
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
*/