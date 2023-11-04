package com.example.geodevineur.condition;

import com.example.geodevineur.tables.Departement;

/* Condition cheking the number of letters in a department name */
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

    public String getSentence(){
        return "Le département possède "+nbChar+" lettres";
    }
}
