package com.example.geodevineur.condition;

import com.example.geodevineur.tables.Departement;
import com.example.geodevineur.tables.Region;

public class RegionCond<E extends Departement> extends Condition<E>{
    private Region region;

    public RegionCond (E e) {
        setAttributes(e);
    }

    protected void setAttributes(E e) {
        region = e.getRegion();
    }

    public boolean checksCondition(E e) {
        return e.getRegion() == region;
    }
}