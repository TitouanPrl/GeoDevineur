package com.example.geodevineur.condition;

import java.util.Random;

import com.example.geodevineur.tables.Departement;
import lombok.Getter;


public class ContainLetterCond<E extends Departement> extends Condition<E>{
    private Random random;
    @Getter
    private char letter;

    public ContainLetterCond(E e) {
        random = new Random();
        setAttributes(e);
    }
    protected void setAttributes(E e) {
        char selected;
        do {
            int a = random.nextInt(e.getName().length());
            selected = e.getName().charAt(a);
        } while ((selected == ' ') || (selected == '-') || (selected == '\''));
        letter = selected;
    }

    public boolean checksCondition(E e) {
        boolean res = false;
        String foreignName = e.getName();
        for (int i = 0; i < foreignName.length() && !res; i++) {
            res = foreignName.charAt(i) == letter;
        }
        return res;
    }
}