package com.example.geodevineur.controllers;

import com.example.geodevineur.condition.*;
import com.example.geodevineur.tables.Departement;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class ConditionController {

    //LOGS A DELETE
    /* Return a list of condition for the quizz, the conditions lead finaly to only one department passed in parameter */
    public List<Condition<Departement>> getRun(List<Departement> allDepartements, Departement cible){
        List<Condition<Departement>> allConditions = new ArrayList<>();
        List<Departement> allDepartementsTemp = allDepartements;
        Condition<Departement> cond = null;
        while(getNbPossible(allDepartementsTemp) != 1){
            int tentatives = 0;
            do {
                tentatives++;
                cond = getNextCond(allDepartementsTemp, cible, cond);
            } while (cond == null && tentatives < 50);

            /* If we can't find the next condition reducing the number of answers possible, we restart from the beginning */
            if (cond == null || (cond.getSentence().contains("numéro") && allConditions.isEmpty())){
                System.out.println("---RESTARTING-RUN-GENERATION------");
                allConditions = new ArrayList<>();
                allDepartementsTemp = allDepartements;
            } else {
                allConditions.add(cond);
            }
        }
        return allConditions;
    }

    /* Return the next condition to reduce the number of answers possible */
    public Condition<Departement> getNextCond(List<Departement> allDepartements, Departement chosen, Condition<Departement> previousCond) {
        Random random = new Random();
        int nbDep = allDepartements.size();

        Departement secondary = allDepartements.get(random.nextInt(nbDep));

        Condition<Departement> cond = null;
        boolean rerun;
        int tentative = 0;

        do {
            tentative++;
            int randCond = random.nextInt(11);
            cond = switch (randCond) {
                case 0 -> new CardinalCond<>(chosen);
                case 1 -> new ContainLetterCond<>(chosen);
                case 2 -> new NbCharactersCond<>(chosen);
                case 3 -> new NeighbourCond<>(chosen, secondary);
                case 4 -> new PoliticCond<>(chosen);
                case 5 -> new PopulationCond<>(chosen, secondary);
                case 6 -> new NumberCond<>(chosen, secondary);
                case 7 -> new RegionCond<>(chosen);
                case 8 -> new SeasideCond<>(chosen);
                case 9 -> new SurfaceCond<>(chosen, secondary);
                case 10 -> new PrefectureCond<>(chosen);
                default -> cond;
            };

            if (previousCond == null)
                rerun = (!isCondGood(cond, allDepartements));
            else if (previousCond.getClass().equals(cond.getClass()))
                rerun = true;
            else
                rerun = (!isCondGood(cond, allDepartements));
            /* isCondGood will set the possible attribute of departements correctly */

            /* If we get an infinite loop because there is no next condition, we return null */
            if (tentative > 50){
                rerun = false;
                cond = null;
            }
        } while (rerun);
        return cond;
    }

    /* Return if the whole list of departments match the condition */
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

        /* Verify if the condition restricts the number of departements left but not too much */
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