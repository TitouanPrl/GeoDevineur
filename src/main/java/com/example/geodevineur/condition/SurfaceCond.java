package com.example.geodevineur.condition;

import java.util.Random;

import com.example.geodevineur.dep_reg.DepReg;

public class SurfaceCond<E extends DepReg> extends Condition<E> {
    private Random random;
    private E compare;
    private boolean isLess; // If more, isLess is false
    private int threshold;
    
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
                threshold = 5500;
            } else {
                threshold = 6500;
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
