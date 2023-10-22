package com.example.geodevineur.region.condition;

import java.util.Random;
import com.example.geodevineur.region.Region;


public class ContainLetterCond extends Condition{
    private Random random;
    private char letter;

    public ContainLetterCond(Region reg_) {
        random = new Random();
        setAttributes(reg_);
    }
    protected void setAttributes(Region reg) {
        char selected;
        do {
            int a = random.nextInt(reg.getName().length());
            selected = reg.getName().charAt(a);
        } while ((selected == ' ') || (selected == '-') || (selected == '\''));
        letter = selected;
    }

    public boolean checksCondition(Region d) {
        boolean res = false;
        String foreignName = d.getName();
        for (int i = 0; i < foreignName.length() && !res; i++) {
            res = foreignName.charAt(i) == letter;
        }
        return res;
    }
}
