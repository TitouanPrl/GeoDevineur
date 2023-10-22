package com.example.geodevineur.region.condition;
import com.example.geodevineur.region.Region;


public class SeasideCond extends Condition{
    private boolean seaside;

    public SeasideCond (Region reg_) {
        setAttributes(reg_);
    }

    protected void setAttributes(Region reg) {
        seaside = reg.getSeaside();
    }

    public boolean checksCondition(Region reg) {
        return reg.getSeaside() == seaside;
    }
}
