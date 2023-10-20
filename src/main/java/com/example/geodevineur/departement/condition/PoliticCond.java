package com.example.geodevineur.departement.condition;

import com.example.geodevineur.departement.Politic;
import com.example.geodevineur.departement.Departement;

public class PoliticCond extends Condition{
    private Politic politic;

    public PoliticCond (Departement dep_) {
        setAttributes(dep_);
    }

    protected void setAttributes(Departement dep) {
        politic = dep.getPolitic();
    }

    public boolean checksCondition(Departement dep) {
        return dep.getPolitic() == politic;
    }
}
