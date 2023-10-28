package com.example.geodevineur.tables;

import com.example.geodevineur.enumerations.*;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
public class Departement {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int id;

    @Getter
    @ManyToOne
    @JoinColumn(name = "region_id")
    protected Region region;

    @Getter
    @OneToOne(mappedBy = "departement", cascade = CascadeType.ALL)
    protected Prefecture prefecture;

    @Getter
    protected String name;
    @Getter
    protected String number; //"01" -> "2A" -> "95";
    @Getter
    protected int population;
    @Getter
    protected double surface;
    @Getter
    protected boolean seaside;
    @Getter
    protected int neightbours;
    @Getter
    protected Politic politic;

    protected boolean possible = true;
    protected boolean potential = true;

    public Departement (Prefecture prefecture_,
                        String name_,
                        String number_,
                        int pop_,
                        double surf_,
                        boolean sea_,
                        int neigh_,
                        Politic politic_) {
        prefecture = prefecture_;
        name = name_;
        number = number_;
        population = pop_;
        surface = surf_;
        seaside = sea_;
        neightbours = neigh_;
        politic = politic_;
    }

    public Departement() {

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

    public boolean getSeaside() {
        return seaside;
    }

}
