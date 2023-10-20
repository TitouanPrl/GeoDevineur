package com.example.geodevineur.departement.condition;

import com.example.geodevineur.departement.Departement;


public abstract class Condition {
    public abstract boolean checksCondition(Departement d);
    protected abstract void setAttributes(Departement d);
}
