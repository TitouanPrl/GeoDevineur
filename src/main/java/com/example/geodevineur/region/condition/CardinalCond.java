package com.example.geodevineur.region.condition;

import com.example.geodevineur.enumerations.Cardinal;
import com.example.geodevineur.region.Region;

public class CardinalCond extends Condition{
    private Cardinal cardinal;

    public CardinalCond (Region reg_) {
        setAttributes(reg_);
    }

    protected void setAttributes(Region reg) {
        cardinal = reg.getCardinal();
    }

    public boolean checksCondition(Region reg) {
        return reg.getCardinal() == cardinal;
    }
}
