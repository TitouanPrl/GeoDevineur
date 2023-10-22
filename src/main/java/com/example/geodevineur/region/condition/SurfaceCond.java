package com.example.geodevineur.region.condition;

import com.example.geodevineur.region.Region;

import java.util.Random;

public class SurfaceCond extends Condition {
    private Random random;
    private Region compareDep;
    private boolean isLess; // If more, isLess is false
    private int threshold;
    
    public SurfaceCond (Region reg_, Region compareDep_) {
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
            threshold = compareDep.getSurface();
        } else {
            int rand = random.nextInt(2);
            if (rand == 0) {
                threshold = 5500;
            } else {
                threshold = 6500;
            }
        }
        if (threshold < reg.getSurface()) {
            isLess = false;
        } else {
            isLess = true;
        }
    }

    public boolean checksCondition(Region reg) {
        if (isLess) {
            return reg.getSurface() < threshold;
        } else {
            return reg.getSurface() > threshold;
        }
    }
}
