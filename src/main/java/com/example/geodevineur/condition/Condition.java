package com.example.geodevineur.condition;

import com.example.geodevineur.dep_reg.DepReg;


public abstract class Condition<E extends DepReg> {
    public abstract boolean checksCondition(E e);
    protected abstract void setAttributes(E e);
}