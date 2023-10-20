package com.example.geodevineur.departement.condition;

import com.example.geodevineur.departement.Cardinal;
import com.example.geodevineur.departement.Departement;

public class CardinalCond extends Condition{
    private Cardinal cardinal;

    public CardinalCond (Departement dep_) {
        setAttributes(dep_);
    }

    protected void setAttributes(Departement dep) {
        cardinal = dep.getCardinal();
    }

    public boolean checksCondition(Departement dep) {
        return dep.getCardinal() == cardinal;
    }
}
