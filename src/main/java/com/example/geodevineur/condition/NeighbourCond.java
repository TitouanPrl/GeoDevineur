package com.example.geodevineur.condition;

import java.util.Random;

import com.example.geodevineur.tables.Departement;

/* Condition cheking the number of neighbours of the department */
public class NeighbourCond<E extends Departement> extends Condition<E> {
    private Random random;
    private E compare;
    private boolean isLess; // If more, isLess is false
    private int threshold;
    private static final int maxNeighbours = 10;

    public NeighbourCond (E e, E compare_) {
        random = new Random();
        setAttributes(e);
        double probaCompare = .3;
        double rand = random.nextDouble();
        if (rand < probaCompare && e.getNeightbours() != compare_.getNeightbours()) {
            compare = compare_;
        } else {
            compare = null;
        }
    }

    protected void setAttributes(E e) {
        if (compare != null) {
            threshold = compare.getNeightbours();
            if (threshold < e.getNeightbours()) {
                isLess = false;
            } else {
                isLess = true;
            }
        } else {
            do {
                threshold = random.nextInt(NeighbourCond.maxNeighbours) + 1;
            } while (threshold == e.getNeightbours());

            if (threshold < e.getNeightbours()) {
                isLess = false;
            } else {
                isLess = true;
            }
        }
    }

    public String getSentence() {
        if(isLess){
            return "Le département a moins de "+threshold+" voisins";
        } else {
            return "Le département a plus de "+threshold+" voisins";
        }
    }

    public boolean checksCondition(E dep) {
        if (isLess) {
            return dep.getNeightbours() < threshold;
        } else {
            return dep.getNeightbours() > threshold;
        }
    }
}
