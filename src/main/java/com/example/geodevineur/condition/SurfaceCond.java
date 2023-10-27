package com.example.geodevineur.condition;

import java.util.Random;

import com.example.geodevineur.dep_reg.DepReg;
import com.example.geodevineur.dep_reg.Departement;

public class SurfaceCond<E extends DepReg> extends Condition<E> {
    private Random random;
    private E compare;
    private boolean isLess; // If more, isLess is false
    private double threshold;
    
    public SurfaceCond (E e, E compare_) {
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
            threshold = compare.getSurface();
        } else {
            int rand = random.nextInt(2);
            if (rand == 0) {
                threshold = (e instanceof Departement)?5500:30000;
            } else {
                threshold = (e instanceof Departement)?6500:50000;
            }
        }
        if (threshold < e.getSurface()) {
            isLess = false;
        } else {
            isLess = true;
        }
    }

    public boolean checksCondition(E e) {
        if (isLess) {
            return e.getSurface() < threshold;
        } else {
            return e.getSurface() > threshold;
        }
    }
}
