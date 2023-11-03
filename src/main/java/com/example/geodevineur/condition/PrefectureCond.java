package com.example.geodevineur.condition;

import com.example.geodevineur.tables.Departement;
import com.example.geodevineur.tables.Prefecture;

public class PrefectureCond<E extends Departement> extends Condition<E>{
    private Prefecture prefecture;

    public PrefectureCond (E e) {
        setAttributes(e);
    }

    protected void setAttributes(E e) {
        prefecture = e.getPrefecture();
    }

    @Override
    public String getSentence() {
        return null;
    }

    public boolean checksCondition(E e) {
        return e.getPrefecture() == prefecture;
    }
}