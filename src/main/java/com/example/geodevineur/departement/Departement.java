package com.example.geodevineur.departement;

public class Departement {
    private final String name;
    private final int population;
    private final int surface;
    private final Cardinal cardinal;
    private final boolean seaside;
    private final int neightbours;
    private final String id;
    private final Politic politic;
    private boolean possible = true;
    private boolean potential = true;

    public Departement (String name_, int pop_, int surf_, Cardinal card_, boolean sea_, int neigh_, String id_, Politic politic_) {
        name = name_;
        population = pop_;
        surface = surf_;
        cardinal = card_;
        seaside = sea_;
        neightbours = neigh_;
        id = id_;
        politic = politic_;
    }

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
