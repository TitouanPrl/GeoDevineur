package com.example.geodevineur.tables;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;

@Entity
public class Prefecture {

    @Id
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