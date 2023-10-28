package com.example.geodevineur.tables;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
public class Prefecture {

    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int id;

    @Getter
    protected String name;
    @Getter
    protected int population;

    @Getter
    @OneToOne
    @JoinColumn(name = "departement_id")
    protected Departement departement;

    protected boolean possible = true;
    protected boolean potential = true;

    public Prefecture (String name_, int population_) {
        name = name_;
        population = population_;
    }

    public Prefecture() {

    }



    public boolean getPossible() {
        return possible;
    }

    public boolean getPotential() {
        return potential;
    }
}