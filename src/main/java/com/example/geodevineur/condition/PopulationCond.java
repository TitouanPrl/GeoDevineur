package com.example.geodevineur.condition;

import java.util.Random;

import com.example.geodevineur.dep_reg.DepReg;


public class PopulationCond<E extends DepReg> extends Condition<E> {
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
            if (threshold < e.getPopulation()) {
                isLess = false;
            } else {
                isLess = true;
            }
        } else {
            // create appropriate thresholds
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
