package com.example.geodevineur.region.condition;

import java.util.Random;
import com.example.geodevineur.region.Region;


public class PopulationCond extends Condition {
    private Random random;
    private Region compareDep;
    private boolean isLess; // If more, isLess is false
    private int threshold;
    
    public PopulationCond (Region reg_, Region compareDep_) {
        random = new Random();
        setAttributes(reg_);
        double probaCompare = .3;
        double rand = random.nextDouble();
        if (rand < probaCompare) {
            compareDep = compareDep_;
        } else {
            compareDep = null;
        }
    }

    protected void setAttributes(Region reg) {
        if (compareDep != null) {
            threshold = compareDep.getPopulation();
            if (threshold < reg.getPopulation()) {
                isLess = false;
            } else {
                isLess = true;
            }
        } else {
            // create appropriate thresholds
        }
    }

    public boolean checksCondition(Region reg) {
        if (isLess) {
            return reg.getPopulation() < threshold;
        } else {
            return reg.getPopulation() > threshold;
        }
    }
}
