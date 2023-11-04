package com.example.geodevineur.condition;

import java.util.Random;

import com.example.geodevineur.tables.Departement;
import lombok.Getter;

/* Condition cheking if the department has a certain letter */
public class ContainLetterCond<E extends Departement> extends Condition<E>{
    private final Random random;
    @Getter
    private char letter;

    public ContainLetterCond(E e) {
        random = new Random();
        setAttributes(e);
    }
    protected void setAttributes(E e) {
        char selected;
        /* Checking that the char is not a special one */
        do {
            int a = random.nextInt(e.getName().length());
            selected = e.getName().charAt(a);
        } while ((selected == ' ') || (selected == '-') || (selected == '\''));
        letter = selected;
    }

    public String getSentence() {
        return "Le département contient la lettre '" + letter + "'";
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
