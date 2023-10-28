package com.example.geodevineur.tables;

import com.example.geodevineur.enumerations.*;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;

@Entity
public class Departement {

    @Getter
    protected String name;

    @Getter
    @ManyToOne
    @JoinColumn(name = "region_id")
    protected Region region;

    @Getter
    @OneToOne
    @JoinColumn(name = "prefecture_id")
    protected Prefecture prefecture;

    @Getter

    protected int population;
    @Getter
    protected double surface;
    protected boolean seaside;
    @Getter
    protected int neightbours;

    @Getter@Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    protected String id;
    @Getter
    protected Politic politic;

    protected boolean possible = true;
    protected boolean potential = true;

    public Departement (String name_,
                        String id_,
                        int pop_,
                        double surf_,
                        boolean sea_,
                        int neigh_,
                        Politic politic_
                        /*Region region_,
                        Prefecture prefecture_*/) {
        name = name_;
        id = id_;
        population = pop_;
        surface = surf_;
        seaside = sea_;
        neightbours = neigh_;
        politic = politic_;
//        region = region_;
//        prefecture = prefecture_;
    }

    public Departement() {

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

}
