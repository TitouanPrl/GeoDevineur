package com.example.geodevineur.condition;

import java.util.Random;

import com.example.geodevineur.tables.Departement;

/* Condition cheking if the surface of the department is more or less than another */
public class SurfaceCond<E extends Departement> extends Condition<E> {
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

    public String getSentence() {
        if(isLess){
            return "Le département compte moins de "+threshold+" km²";
        } else {
            return "Le département compte plus de "+threshold+" km²";
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
