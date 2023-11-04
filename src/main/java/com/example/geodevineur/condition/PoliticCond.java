package com.example.geodevineur.condition;

import com.example.geodevineur.tables.Departement;
import com.example.geodevineur.enumerations.Politic;

/* Condition cheking the department policy */
public class PoliticCond<E extends Departement> extends Condition<E>{
    private Politic politic;

    public PoliticCond (E e) {
        setAttributes(e);
    }

    protected void setAttributes(E e) {
        politic = e.getPolitic();
    }

    public String getSentence() {
        return "Le département vote " + politic.toString() + " en majorité";
    }

    public boolean checksCondition(E e) {
        return e.getPolitic() == politic;
    }
}
