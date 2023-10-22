package com.example.geodevineur.region.condition;

import com.example.geodevineur.enumerations.Politic;
import com.example.geodevineur.region.Region;

public class PoliticCond extends Condition{
    private Politic politic;

    public PoliticCond (Region reg_) {
        setAttributes(reg_);
    }

    protected void setAttributes(Region reg) {
        politic = reg.getPolitic();
    }

    public boolean checksCondition(Region reg) {
        return reg.getPolitic() == politic;
    }
}
