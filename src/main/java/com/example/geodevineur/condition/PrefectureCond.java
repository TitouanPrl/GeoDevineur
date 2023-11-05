package com.example.geodevineur.condition;

import com.example.geodevineur.tables.Departement;
import com.example.geodevineur.tables.Prefecture;

/* Condition cheking the prefecture of the department */
public class PrefectureCond<E extends Departement> extends Condition<E>{
    private Prefecture prefecture;

    public PrefectureCond (E e) {
        setAttributes(e);
    }

    protected void setAttributes(E e) {
        prefecture = e.getPrefecture();
    }

    public String getSentence() {
        return "Le département a pour préfecture "+prefecture.getName();
    }

    public boolean checksCondition(E e) {
        return e.getPrefecture().getName().equals(prefecture.getName());
    }
}
