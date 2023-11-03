package com.example.geodevineur.couzi;

import com.example.geodevineur.tables.Departement;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class Condition {

    @Getter
    private List<Departement> validateCond;
    @Getter@Setter
    private String attribut; //population,surface,neightbours(int) | seaside,politic(binary)
    @Getter@Setter
    private int delta; //5%-10%-25%-50%-0%(for binary)
    @Getter@Setter
    private int value;
    public Condition(int delta_, String attribut_){
        this.delta = delta_;
        this.attribut = attribut_;
    }

//    public String getSentence(){
//
//    }

    public void setValidatingDeps(){

    }



}
