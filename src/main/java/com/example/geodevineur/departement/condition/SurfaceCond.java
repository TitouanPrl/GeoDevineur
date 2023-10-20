package com.example.geodevineur.departement.condition;

import com.example.geodevineur.departement.Departement;

import java.util.Random;

public class SurfaceCond extends Condition {
    private Random random;
    private Departement compareDep;
    private boolean isLess; // If more, isLess is false
    private int threshold;
    
    public SurfaceCond (Departement dep_, Departement compareDep_) {
        random = new Random();
        setAttributes(dep_);
        double probaCompare = .3;
        double rand = random.nextDouble();
        if (rand < probaCompare) {
            compareDep = compareDep_;
        } else {
            compareDep = null;
        }
    }

    protected void setAttributes(Departement dep) {
        if (compareDep != null) {
            threshold = compareDep.getSurface();
        } else {
            int rand = random.nextInt(2);
            if (rand == 0) {
                threshold = 5500;
            } else {
                threshold = 6500;
            }
        }
        if (threshold < dep.getSurface()) {
            isLess = false;
        } else {
            isLess = true;
        }
    }

    public boolean checksCondition(Departement dep) {
        if (isLess) {
            return dep.getSurface() < threshold;
        } else {
            return dep.getSurface() > threshold;
        }
    }
}
