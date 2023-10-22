package com.example.geodevineur.condition;

import com.example.geodevineur.dep_reg.DepReg;
import com.example.geodevineur.enumerations.Cardinal;

public class CardinalCond<E extends DepReg> extends Condition<E>{
    private Cardinal cardinal;

    public CardinalCond (E e) {
        setAttributes(e);
    }

    protected void setAttributes(E e) {
        cardinal = e.getCardinal();
    }

    public boolean checksCondition(E e) {
        return e.getCardinal() == cardinal;
    }
}
