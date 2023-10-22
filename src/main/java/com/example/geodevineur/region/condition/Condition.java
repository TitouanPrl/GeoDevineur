package com.example.geodevineur.region.condition;

import com.example.geodevineur.region.Region;


public abstract class Condition {
    public abstract boolean checksCondition(Region d);
    protected abstract void setAttributes(Region d);
}
