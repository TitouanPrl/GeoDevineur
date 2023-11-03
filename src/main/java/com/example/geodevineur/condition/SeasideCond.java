package com.example.geodevineur.condition;
import com.example.geodevineur.tables.Departement;


public class SeasideCond<E extends Departement> extends Condition<E>{
    private boolean seaside;

    public SeasideCond (E e) {
        setAttributes(e);
    }

    protected void setAttributes(E e) {
        seaside = e.isSeaside();
    }

    public String getSentence() {
        if(seaside){
            return "Le département se situe en bord de mer";
        } else {
            return "Le département ne se situe pas en bord de mer";
        }
    }

    public boolean checksCondition(E e) {
        return e.isSeaside() == seaside;
    }
}
