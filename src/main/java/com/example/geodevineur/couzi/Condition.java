/*
package com.example.geodevineur.couzi;

import com.example.geodevineur.Format;
import com.example.geodevineur.tables.Departement;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Condition {

    @Getter@Setter
    private List<Departement> validateCond;
    @Getter@Setter
    private Departement cible;
    @Getter@Setter
    private String attribut; //population,surface,neightbours(int) | seaside,politic(binary)
    @Getter@Setter
    private int delta; //5%-10%-25%-50%-0%(for binary)

    private int sentence;

    public Condition(int delta_, String attribut_, Departement departement_){
        this.delta = delta_;
        this.attribut = attribut_;
        this.cible = departement_;
        this.validateCond = new ArrayList<>();
    }

    public Condition(){

    }


    public String getSentence(){
        String sentence;
        switch (getAttribut()){
            case "population":
                System.out.println("delta="+delta);
                if(isDeltaPositive()){
                    sentence = "Le departement contient moins de " + newPopulation() + " habitants";
                } else {
                    sentence = "Le departement contient plus de " + newPopulation() + " habitants";
                }
                break;
            case "surface":
                if(isDeltaPositive()){
                    sentence = "Le departement a une superficie de moins de " + newSurface() + " km²";
                } else {
                    sentence = "Le departement a une superficie de plus de " + newSurface() + " km²";
                }
                break;
            case "neightbours":
                if(isDeltaPositive()){
                    sentence = "Le departement a au maximum " + newNeightbours() + " voisins";
                } else {
                    sentence = "Le departement a au minimum " + newNeightbours() + " voisins";
                }
                break;
            case "politic":
                sentence = "Le departement vote en majorité " + newPolitic();
                break;
            case "seaside":
                if(getCible().getSeaside()){
                    sentence = "Le departement se situe en bord de mer";
                } else {
                    sentence = "Le departement ne se situe pas en bord de mer";
                }
                break;
            default:
                sentence = "default";
                break;
        }
        return sentence;
    }

    public boolean isDepartementValidating(Departement departement){
        boolean status;
        switch (getAttribut()){
            case "population":
                if(isDeltaPositive()){
                    status = (departement.getPopulation() <= getCible().getPopulation()*getDelta()/100);
                } else {
                    status = (departement.getPopulation() >= getCible().getPopulation()*getDelta()/100);
                }
                break;
            case "surface":
                if(isDeltaPositive()){
                    status = (departement.getSurface() <= getCible().getSurface()*getDelta()/100);
                } else {
                    status = (departement.getSurface() >= getCible().getSurface()*getDelta()/100);
                }
                break;
            case "neightbours":
                if(isDeltaPositive()){
                    status = (departement.getNeightbours() <= getCible().getNeightbours()*getDelta()/100);
                } else {
                    status = (departement.getNeightbours() >= getCible().getNeightbours()*getDelta()/100);
                }
                break;
            case "politic":
                status = (departement.getPolitic().equals(getCible().getPolitic()));
                break;
            case "seaside":
                status = (departement.getSeaside() == getCible().getSeaside());
                break;
            default:
                status = false;
                break;
        }
        return status;
    }

    public boolean isDeltaPositive(){
        return (getDelta() > 0);
    }

    public boolean isAttributTypeNumeric(){
        return (Arrays.asList("population","surface","neightbouts").contains(getAttribut()));
    }

    public String newPopulation(){
        int pop = Format.roundInt(getCible().getPopulation() + getCible().getPopulation()*getDelta()/100,1000);
        return Format.intToFormatedString(pop);
    }

    public String newSurface(){
        double surface = Format.round(getCible().getSurface(),2);
        return String.valueOf(surface + surface*getDelta()/100);
    }

    public String newNeightbours(){
        return String.valueOf(getCible().getNeightbours() + getCible().getNeightbours()*getDelta()/100);
    }

    public String newSeaside(){
        return String.valueOf(getCible().getSeaside());
    }

    public String newPolitic(){
        return getCible().getPolitic().toString();
    }
}
*/