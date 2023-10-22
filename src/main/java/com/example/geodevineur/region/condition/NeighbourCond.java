package com.example.geodevineur.region.condition;

import java.util.Random;
import com.example.geodevineur.region.Region;


public class NeighbourCond extends Condition {
    private Random random;
    private Region compareDep;
    private boolean isLess; // If more, isLess is false
    private int threshold;
    private static final int maxNeighbours = 10;
    
    public NeighbourCond (Region reg_, Region compareDep_) {
        random = new Random();
        setAttributes(reg_);
        double probaCompare = .3;
        double rand = random.nextDouble();
        if (rand < probaCompare && reg_.getNeightbours() != compareDep_.getNeightbours()) {
            compareDep = compareDep_;
        } else {
            compareDep = null;
        }
    }

    protected void setAttributes(Region reg) {
        if (compareDep != null) {
            threshold = compareDep.getNeightbours();
            if (threshold < reg.getNeightbours()) {
                isLess = false;
            } else {
                isLess = true;
            }
        } else {
            do {
                threshold = random.nextInt(NeighbourCond.maxNeighbours) + 1;
            } while (threshold == reg.getNeightbours());

            if (threshold < reg.getNeightbours()) {
                isLess = false;
            } else {
                isLess = true;
            }
        }
    }

    public boolean checksCondition(Region reg) {
        if (isLess) {
            return reg.getNeightbours() < threshold;
        } else {
            return reg.getNeightbours() > threshold;
        }
    }
}
