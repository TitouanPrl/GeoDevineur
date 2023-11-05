package com.example.geodevineur.condition;

import java.util.Random;

import com.example.geodevineur.tables.Departement;

/* Condition cheking if the number of the department is more or less than another */
public class NumberCond<E extends Departement> extends Condition<E> {
    private Random random;
    private E compare;
    private boolean isLess = false; /* If more, isLess is false */
    private String threshold;
    private static final int maxId = 95;

    public NumberCond(E e, E compare_) {
        random = new Random();
        setAttributes(e);
        double probaCompare = .3;
        double rand = random.nextDouble();
        if (rand < probaCompare) {
            compare = compare_;
        } else {
            compare = null;
        }
    }

    protected void setAttributes(E e) {
        if (compare != null) {
            threshold = compare.getNumber();
            if (compareId(threshold,e.getNumber()) < 0) {
                isLess = true;
                return;
            } else if (compareId(threshold,e.getNumber()) > 0) {
                isLess = false;
                return;
            }
        }

        int number = random.nextInt(NumberCond.maxId) + 1;

        if (number == 20) {
            threshold = "2A";
        } else if (number < 10) {
            threshold = "0" + number;
        } else {
            threshold = Integer.toString(number);
        }

        if (compareId(threshold,e.getNumber()) < 0) {
            isLess = true;
        } else if (compareId(threshold,e.getNumber()) > 0) {
            isLess = false;
        }
    }

    public String getSentence() {
        if(!isLess){
            return "Le numéro du département est inférieur au "+threshold;
        } else {
            return "Le numéro du département est supérieur au "+threshold;
        }
    }

    public boolean checksCondition(E e) {
        if (isLess) {
            return compareId(e.getNumber(), threshold) < 0;
        } else {
            return compareId(e.getNumber(), threshold) > 0;
        }
    }

    private int compareId(String id1, String id2) {
        if (Character.compare(id1.charAt(0), id2.charAt(0)) != 0)
            return Character.compare(id1.charAt(0), id2.charAt(0));
        return Character.compare(id1.charAt(1), id2.charAt(1));

    }
}
