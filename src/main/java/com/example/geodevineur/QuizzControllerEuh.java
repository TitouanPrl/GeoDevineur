/*
package com.example.geodevineur;

import java.util.Random;

import com.example.geodevineur.condition.Condition;
import com.example.geodevineur.tables.Departement;


public class IdCond<E extends Departement> extends Condition<E> {
    private Random random;
    private E compare;
    private boolean isLess; // If more, isLess is false
    private int threshold;
    private static final int maxId = 95;

    public IdCond (E e, E compare_) {
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

    */
/*protected void setAttributes(E e) {
        if (compare != null) {
            threshold = compare.getId();
            if (threshold < e.getId()) {
                isLess = true;
                return;
            } else if (threshold > e.getId()) {
                isLess = false;
                return;
            }
        }

        Integer number = random.nextInt(IdCond.maxId) + 1;
        if (number == 20) {
            threshold = "2A";
        } else {
            threshold = number.toString();
        }
    }

    public boolean checksCondition(E e) {
        if (isLess) {
            return compareId(e.getId(), threshold) < 0;
        } else {
            return compareId(e.getId(), threshold) > 0;
        }
    }*//*


    private int compareId(String id1, String id2) {
        if (Character.compare(id1.charAt(0), id2.charAt(0)) != 0)
            return Character.compare(id1.charAt(0), id2.charAt(0));
        return Character.compare(id1.charAt(1), id2.charAt(1));

    }
}*/
