package com.example.geodevineur;

import java.util.Random;

public class NeighbourCond extends Condition {
    private Random random;
    private Departement compareDep;
    private boolean isLess; // If more, isLess is false
    private int threshold;
    private static final int maxNeighbours = 10;
    
    public NeighbourCond (Departement dep_, Departement compareDep_) {
        random = new Random();
        setAttributes(dep_);
        double probaCompare = .3;
        double rand = random.nextDouble();
        if (rand < probaCompare && dep_.getNeightbours() != compareDep_.getNeightbours()) {
            compareDep = compareDep_;
        } else {
            compareDep = null;
        }
    }

    protected void setAttributes(Departement dep) {
        if (compareDep != null) {
            threshold = compareDep.getNeightbours();
            if (threshold < dep.getNeightbours()) {
                isLess = false;
            } else {
                isLess = true;
            }
        } else {
            do {
                threshold = random.nextInt(NeighbourCond.maxNeighbours) + 1;
            } while (threshold == dep.getNeightbours());

            if (threshold < dep.getNeightbours()) {
                isLess = false;
            } else {
                isLess = true;
            }
        }
    }

    public boolean checksCondition(Departement dep) {
        if (isLess) {
            return dep.getNeightbours() < threshold;
        } else {
            return dep.getNeightbours() > threshold;
        }
    }
}
