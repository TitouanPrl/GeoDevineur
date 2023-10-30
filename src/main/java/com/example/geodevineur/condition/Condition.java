package com.example.geodevineur.condition;

import com.example.geodevineur.tables.Departement;


public abstract class Condition<E extends Departement> {
    public abstract boolean checksCondition(E e);
    protected abstract void setAttributes(E e);
}
