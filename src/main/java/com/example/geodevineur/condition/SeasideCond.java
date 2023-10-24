package com.example.geodevineur.condition;
import com.example.geodevineur.dep_reg.DepReg;


public class SeasideCond<E extends DepReg> extends Condition<E>{
    private boolean seaside;

    public SeasideCond (E e) {
        setAttributes(e);
    }

    protected void setAttributes(E e) {
        seaside = e.getSeaside();
    }

    public boolean checksCondition(E e) {
        return e.getSeaside() == seaside;
    }
}
