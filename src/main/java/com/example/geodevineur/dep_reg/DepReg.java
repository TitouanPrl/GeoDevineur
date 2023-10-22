package com.example.geodevineur.dep_reg;

import com.example.geodevineur.enumerations.*;

public abstract class DepReg {
    protected String name;
    protected int population;
    protected int surface;
    protected Cardinal cardinal;
    protected boolean seaside;
    protected int neightbours;
    protected String id;
    protected Politic politic;
    protected boolean possible = true;
    protected boolean potential = true;

    public Cardinal getCardinal() {
        return cardinal;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getNeightbours() {
        return neightbours;
    }

    public Politic getPolitic() {
        return politic;
    }

    public int getPopulation() {
        return population;
    }

    public int getSurface() {
        return surface;
    }

    public boolean getSeaside() {
        return seaside;
    }

    public void setPossible(boolean possible) {
        this.possible = possible;
    }

    public void setPotential(boolean potential) {
        this.potential = potential;
    }

    public boolean getPossible() {
        return possible;
    }

    public boolean getPotential() {
        return potential;
    }
}
