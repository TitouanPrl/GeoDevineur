package com.example.geodevineur.condition;

import com.example.geodevineur.enumerations.Cardinal;
import com.example.geodevineur.tables.Departement;

public class CardinalCond<E extends Departement> extends Condition<E>{
    private Cardinal cardinal;

    public CardinalCond (E e) {
        setAttributes(e);
    }

    protected void setAttributes(E e) {
        cardinal = e.getRegion().getCardinal();
    }

    public String getSentence() {
        return "Le département se situe au " + cardinal.toString();
    }

    public boolean checksCondition(E e) {
        return e.getRegion().getCardinal() == cardinal;
    }
}
