package com.example.geodevineur.condition;

import com.example.geodevineur.tables.Departement;
import com.example.geodevineur.tables.Region;

/* Condition cheking the region of the department */
public class RegionCond<E extends Departement> extends Condition<E>{
    private Region region;

    public RegionCond (E e) {
        setAttributes(e);
    }

    protected void setAttributes(E e) {
        region = e.getRegion();
    }

    public String getSentence() {
        return "Le département a pour région "+region.getName();
    }

    public boolean checksCondition(E e) {
        return e.getRegion().getName().equals(region.getName());
    }
}
