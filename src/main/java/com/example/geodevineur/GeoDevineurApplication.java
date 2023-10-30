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
import com.example.geodevineur.tables.Departement;
import com.example.geodevineur.enumerations.Cardinal;
import com.example.geodevineur.enumerations.Politic;

@SpringBootApplication
public class GeoDevineurApplication {

    public static void main(String[] args) {

        SpringApplication.run(GeoDevineurApplication.class, args);
    }

    /* Génération aléatoire de la question (restreignant le champs des possibles) */
    public Condition<Departement> questionChoice(Departement solution, int possibilites) {
        /* On choisit aléatoirement le type de question */
        Random random = new Random();
        int randomInt = random.nextInt(9);

        Condition<Departement> cond;

        switch (randomInt) {
            /* Puis on définit l'argument de sorte à ce que la question restreigne le champs des possibles
             * Si ce n'est pas possible, alors on choisit une autre question
             */
            /* Cardinal */
            case 0:
                Cardinal c = solution.getRegion().getCardinal();
                /* compter le nb de département qui ont ce cardinal */
                if (nbMatch < possibilites) {
                    return new CardinalCond<Departement>(solution);
                }

                else {
                    return questionChoice(solution, possibilites);
                }

                break;

            /* Contient telle lettre */
            case 1:
                /* On prend un caractère du nom au hasard
                 * AU LIEU DE PRENDRE AU HASARD IL FAUDRAIT EN PRENDRE UN QUI RESTREINT
                 * PASSER EN PARAM DES CONDITIONS LE NB DE POSSIBILITES ?
                 */
                ContainLetterCond<Departement> letterCond = new ContainLetterCond<Departement>(solution);
                char letter = letterCond.getLetter();

                /* compter le nb de département qui ont cette lettre dans la BDD */
                if (nbMatch < possibilites) {
                    return letterCond;
                }

                else {
                    return questionChoice(solution, possibilites);
                }

                break;

            /* Num du département */
            /* PAS CAPTE LE SYSTEME DE CHOIX DE MAEL */
            case 2:
                /* compter le nb de département qui sont inférieurs/supérieurs à ce num */
                if (nbMatch < possibilites) {
                    return new CardinalCond<Departement>(solution);
                }

                else {
                    return questionChoice(solution, possibilites);
                }

                break;

            /* Nb char */
            /* VOIR COMMENT DEFINIR LES PALLIERS */
            case 3:
                /* compter le nb de département qui ont plus ou moins que X lettres */
                if (nbMatch < possibilites) {
                    return new CardinalCond<Departement>(solution);
                }

                else {
                    return questionChoice(solution, possibilites);
                }

                break;

            /* Nb voisins */
            case 4:
                /* PAS CAPTE LE SYSTEME DE CHOIX DE MAEL */
                int voisins = solution.getNeightbours();
                /* compter le nb de département qui ont ce nb de voisins */
                if (nbMatch < possibilites) {
                    return new NeighbourCond<Departement>(solution);
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
                    return new PoliticCond<Departement>(solution);
                }

                else {
                    return questionChoice(solution, possibilites);
                }

                break;

            /* Population */
            /* PAS CAPTE LE SYSTEME DE CHOIX DE MAEL */
            case 6:
                /* compter le nb de département qui match le palier */
                if (nbMatch < possibilites) {
                    return new CardinalCond<Departement>(solution);
                }

                else {
                    return questionChoice(solution, possibilites);
                }

                break;

            /* Cotier */
            case 7:
                boolean cote = solution.isSeaside();
                /* compter le nb de département qui sont pareils */
                if (nbMatch < possibilites) {
                    return new SeasideCond<Departement>(solution);
                }

                else {
                    return questionChoice(solution, possibilites);
                }

                break;

            /* Surface */
            /* PAS CAPTE LE SYSTEME DE CHOIX DE MAEL */
            case 8:
                /* compter le nb de département qui match ce palier */
                if (nbMatch < possibilites) {
                    return new CardinalCond<Departement>(solution);
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