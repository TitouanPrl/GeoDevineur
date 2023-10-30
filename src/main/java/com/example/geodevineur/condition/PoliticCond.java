package com.example.geodevineur.condition;

import com.example.geodevineur.tables.Departement;
import com.example.geodevineur.enumerations.Politic;

public class PoliticCond<E extends Departement> extends Condition<E>{
    private Politic politic;

    public PoliticCond (E e) {
        setAttributes(e);
    }

    protected void setAttributes(E e) {
        politic = e.getPolitic();
    }

    public boolean checksCondition(E e) {
        return e.getPolitic() == politic;
    }
}
