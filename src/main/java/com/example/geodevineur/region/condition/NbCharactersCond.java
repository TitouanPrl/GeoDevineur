package com.example.geodevineur.region.condition;

import com.example.geodevineur.region.Region;

public class NbCharactersCond extends Condition{
    private int nbChar;

    public NbCharactersCond(Region reg_) {
        setAttributes(reg_);
    }

    protected void setAttributes(Region reg) {
        nbChar = reg.getName().length();
    }

    public boolean checksCondition(Region d) {
        return (d.getName().length() == nbChar);
    }
}
