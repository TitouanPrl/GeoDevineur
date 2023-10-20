package com.example.geodevineur;
public class SeasideCond extends Condition{
    private boolean seaside;

    public SeasideCond (Departement dep_) {
        setAttributes(dep_);
    }

    protected void setAttributes(Departement dep) {
        seaside = dep.getSeaside();
    }

    public boolean checksCondition(Departement dep) {
        return dep.getSeaside() == seaside;
    }
}
