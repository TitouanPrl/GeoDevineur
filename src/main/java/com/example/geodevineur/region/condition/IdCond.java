package com.example.geodevineur.region.condition;

import java.util.Random;
import com.example.geodevineur.region.Region;


public class IdCond extends Condition {
    private Random random;
    private Region compareDep;
    private boolean isLess; // If more, isLess is false
    private String threshold;
    private static final int maxId = 95;

    public IdCond (Region reg_, Region compareDep_) {
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
            threshold = compareDep.getId();
            if (compareId(threshold, reg.getId()) < 0) {
                isLess = true;
                return;
            } else if (compareId(threshold, reg.getId()) > 0) {
                isLess = false;
                return;
            }
        }

        Integer number = random.nextInt(IdCond.maxId) + 1;
        if (number == 20) {
            threshold = "2A";
        } else {
            threshold = number.toString();
        }
    }

    public boolean checksCondition(Region reg) {
        if (isLess) {
            return compareId(reg.getId(), threshold) < 0;
        } else {
            return compareId(reg.getId(), threshold) > 0;
        }
    }

    private int compareId(String id1, String id2) {
        if (Character.compare(id1.charAt(0), id2.charAt(0)) != 0)
            return Character.compare(id1.charAt(0), id2.charAt(0));
        return Character.compare(id1.charAt(1), id2.charAt(1));

    }
}
