package com.example.geodevineur.dep_reg;

import com.example.geodevineur.enumerations.*;

import jakarta.annotation.Generated;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public abstract class DepReg {
    protected String name;
    protected int population;
    protected double surface;
    protected Cardinal cardinal;
    protected boolean seaside;
    protected int neightbours;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    public double getSurface() {
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
