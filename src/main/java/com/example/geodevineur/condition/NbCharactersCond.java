package com.example.geodevineur.condition;

import com.example.geodevineur.tables.Departement;

public class NbCharactersCond<E extends Departement> extends Condition<E>{
    private int nbChar;

    public NbCharactersCond(E e) {
        setAttributes(e);
    }

    protected void setAttributes(E e) {
        nbChar = e.getName().length();
    }

    public boolean checksCondition(E e) {
        return (e.getName().length() == nbChar);
    }
}
