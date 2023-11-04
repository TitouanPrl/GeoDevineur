package com.example.geodevineur.condition;

import java.util.Random;

import com.example.geodevineur.Format;
import com.example.geodevineur.tables.Departement;

/* Condition cheking if the population of the department is more or less then a number */
public class PopulationCond<E extends Departement> extends Condition<E> {
    private Random random;
    private E compare;
    private boolean isLess; // If more, isLess is false
    private int threshold;
    
    public PopulationCond (E e, E compare_) {
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
            threshold = compare.getPopulation();
        } else {
            int rand = random.nextInt(2);
            if (rand == 0) {
                threshold = (e instanceof Departement)?300000:3500000;
            } else {
                threshold = (e instanceof Departement)?700000:6000000;
            }
        }
        if (threshold < e.getPopulation()) {
            isLess = false;
        } else {
            isLess = true;
        }
    }

    public String getSentence() {
        if(isLess){
            return "Le département a moins de " + Format.intToFormatedString(threshold) + " habitants";
        } else {
            return "Le département a plus de " + Format.intToFormatedString(threshold) + " habitants";
        }
    }

    public boolean checksCondition(E e) {
        if (isLess) {
            return e.getPopulation() < threshold;
        } else {
            return e.getPopulation() > threshold;
        }
    }
}
