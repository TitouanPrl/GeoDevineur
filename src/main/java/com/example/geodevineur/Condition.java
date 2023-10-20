package com.example.geodevineur;

public abstract class Condition {
    public abstract boolean checksCondition(Departement d);
    protected abstract void setAttributes(Departement d);
}
