package com.example.geodevineur;

import java.util.Random;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.geodevineur.condition.CardinalCond;
import com.example.geodevineur.condition.Condition;
import com.example.geodevineur.condition.ContainLetterCond;
import com.example.geodevineur.condition.NeighbourCond;
import com.example.geodevineur.condition.PoliticCond;
import com.example.geodevineur.condition.SeasideCond;
import com.example.geodevineur.dep_reg.DepReg;
import com.example.geodevineur.dep_reg.Departement;
import com.example.geodevineur.enumerations.Cardinal;
import com.example.geodevineur.enumerations.Politic;

@SpringBootApplication
public class GeoDevineurApplication {

    public static void main(String[] args) {

        SpringApplication.run(GeoDevineurApplication.class, args);
    }

    /* Génération aléatoire de la question (restreignant le champs des possibles) */
    public Condition questionChoice(Departement solution, int possibilites) {
        /* On choisit le type de question */
        Random random = new Random();
        int randomInt = random.nextInt(9);

        Condition cond;

        switch (randomInt) {
            /* Cardinal */
            case 0:
                Cardinal c = solution.getCardinal();
                /* compter le nb de département qui ont ce cardinal */
                if (nbMatch < possibilites) {
                    return new CardinalCond<DepReg>(solution);
                }

                else {
                    return questionChoice(solution, possibilites);
                }
                
                break;
            
            /* Contient telle lettre */
            case 1:
            /* On prend un caractère du nom au hasard */
            /* A REVOIR vu que fait aussi dans la condition */
                int taille = solution.getName().length();
                random = new Random();
                randomInt = random.nextInt(taille);
                char lettre = solution.getName().toCharArray()[randomInt];

                /* compter le nb de département qui ont cette lettre */
                if (nbMatch < possibilites) {
                    return new ContainLetterCond<DepReg>(solution);
                }

                else {
                    return questionChoice(solution, possibilites);
                }   

                break;

            /* Num du département */
            case 2:
                Cardinal c = solution.getCardinal();
                /* compter le nb de département qui ont ce cardinal */
                if (nbMatch < possibilites) {
                    return new CardinalCond<DepReg>(solution);
                }

                else {
                    return questionChoice(solution, possibilites);
                }
                
                break;

            /* Nb char */
            /* VOIR COMMENT DEFINIR LES PALLIERS */
            case 3:
                Cardinal c = solution.getCardinal();
                /* compter le nb de département qui ont ce cardinal */
                if (nbMatch < possibilites) {
                    return new CardinalCond<DepReg>(solution);
                }

                else {
                    return questionChoice(solution, possibilites);
                }
                
                break;

            /* Nb voisins */
            case 4:
            /* MAEL PAS FAIT PAREIL ? */
                int voisins = solution.getNeightbours();
                /* compter le nb de département qui ont ce nb de voisins */
                if (nbMatch < possibilites) {
                    return new NeighbourCond<DepReg>(solution);
                }

                else {
                    return questionChoice(solution, possibilites);
                }
                
                break;

            /* Politique */
            case 5:
                Politic p = solution.getPolitic();
                /* compter le nb de département qui ont cette politique */
                if (nbMatch < possibilites) {
                    return new PoliticCond<DepReg>(solution);
                }

                else {
                    return questionChoice(solution, possibilites);
                }
                
                break;

            /* Population */
            /* VOIR COMMENT DEFINIR LES PALLIERS */
            case 6:
                Cardinal c = solution.getCardinal();
                /* compter le nb de département qui ont ce cardinal */
                if (nbMatch < possibilites) {
                    return new CardinalCond<DepReg>(solution);
                }

                else {
                    return questionChoice(solution, possibilites);
                }
                
                break;

            /* Cotier */
            case 7:
                boolean cote = solution.getSeaside();
                /* compter le nb de département qui sont pareils */
                if (nbMatch < possibilites) {
                    return new SeasideCond<DepReg>(solution);
                }

                else {
                    return questionChoice(solution, possibilites);
                }
                
                break;

            /* Surface */
            /* VOIR COMMENT DEFINIR LES PALLIERS */
            case 8:
                Cardinal c = solution.getCardinal();
                /* compter le nb de département qui ont ce cardinal */
                if (nbMatch < possibilites) {
                    return new CardinalCond<DepReg>(solution);
                }

                else {
                    return questionChoice(solution, possibilites);
                }
                
                break;
        
            default:
                break;
        }

    }

}