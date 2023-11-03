package com.example.geodevineur.controllers;

import com.example.geodevineur.condition.*;
import com.example.geodevineur.tables.Departement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class ConditionController {

    @Autowired
    private DepartementController departementController;

    public ConditionController(DepartementController departementController_){
        this.departementController = departementController_;
    }

    public Condition<Departement> getNextCond() {
        Random random = new Random();
        List<Departement> allDepartements = departementController.getAll();
        int nbDep = allDepartements.size();

        Departement chosen;

        do {
            int randIndex = random.nextInt(nbDep);
            chosen = allDepartements.get(randIndex);
        } while(!chosen.getPossible());

        Departement secondary;
        int randIndex = random.nextInt(nbDep);
        secondary = allDepartements.get(randIndex);

        Condition<Departement> cond = null;

        do {
            int randCond = random.nextInt(10);
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
            }

            //isCondGood will set the possible attribute of departements correctly

        } while (!isCondGood(cond, allDepartements));
        return cond;
    }

    private boolean isCondGood(Condition<Departement> cond, List<Departement> allDepartements) {
        int countPossible = 0;
        int countPotential = 0;

        for (Departement dep : allDepartements) {
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
}