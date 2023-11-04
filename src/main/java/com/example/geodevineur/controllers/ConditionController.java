package com.example.geodevineur.controllers;

import com.example.geodevineur.condition.*;
import com.example.geodevineur.tables.Departement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class ConditionController {

    @Autowired
    private DepartementController departementController;

    public ConditionController(DepartementController departementController_){
        this.departementController = departementController_;
    }

    public List<Condition<Departement>> getRun(List<Departement> allDepartements, Departement cible){
        List<Condition<Departement>> allConditions = new ArrayList<>();
        while(getNbPossible(allDepartements) != 1){
            System.out.println("nb possible ="+getNbPossible(allDepartements));
            Condition<Departement> cond = getNextCond(allDepartements, cible);
            allConditions.add(cond);
            System.out.println(cond.getSentence());
        }
        int i=0;
        for(Departement dep : allDepartements){
            if(dep.getPossible()){
                System.out.println("dernier restant "+dep.getName());
            }
        }
        System.out.println("total conds : "+allConditions.size());
        return allConditions;
    }

    public Condition<Departement> getNextCond(List<Departement> allDepartements, Departement chosen) {
        Random random = new Random();
        int nbDep = allDepartements.size();

        Departement secondary;
        int randIndex = random.nextInt(nbDep);
        secondary = allDepartements.get(randIndex);

        Condition<Departement> cond = null;

        do {
            int randCond = random.nextInt(11);
            switch (randCond) {
                case 0:
                    cond = new CardinalCond<>(chosen);
                    break;
                case 1:
                    cond = new ContainLetterCond<>(chosen);
                    break;
                case 2:
                    cond = new NbCharactersCond<>(chosen);
                    break;
                case 3:
                    cond = new NeighbourCond<>(chosen, secondary);
                    break;
                case 4:
                    cond = new PoliticCond<>(chosen);
                    break;
                case 5:
                    cond = new PopulationCond<>(chosen, secondary);
                    break;
                case 6:
                    cond = new NumberCond<>(chosen, secondary);
                    break;
                case 7:
                    cond = new RegionCond<>(chosen);
                    break;
                case 8:
                    cond = new SeasideCond<>(chosen);
                    break;
                case 9:
                    cond = new SurfaceCond<>(chosen, secondary);
                    break;
                case 10:
                    cond = new PrefectureCond<>(chosen);
                    break;
            }

            //isCondGood will set the possible attribute of departements correctly

        } while (!isCondGood(cond, allDepartements));
        return cond;
    }

    private boolean isCondGood(Condition<Departement> cond, List<Departement> allDepartements) {
        int countPossible = 0;
        int countPotential = 0;

        for (Departement dep : allDepartements) {
            dep.setPotential(dep.getPossible());
            if (dep.getPossible()) {
                countPossible++;
            }
            dep.setPotential(cond.checksCondition(dep));
            if (dep.getPotential()) {
                countPotential++;
            }
        }

        // verify if the condition restricts the number of departements left but not too much
        if (countPotential < countPossible && countPotential > countPossible / 3) {
            for (Departement dep : allDepartements) {
                dep.setPossible(dep.getPotential());
            }
            return true;
        }
        return false;
    }

    public int getNbPossible(List<Departement> allDeps){
        int i=0;
        for(Departement d : allDeps){
            if(d.getPossible()){
                i++;
            }
        }
        return i;
    }
}