package com.example.geodevineur.tables;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
public class Prefecture implements Comparable<Prefecture> {

    @Id@Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int id;

    @Getter@Setter
    @OneToOne(fetch = FetchType.LAZY, targetEntity = Departement.class)
    @JoinColumn(name = "departement_id")
    protected Departement departement;

    @Getter@Setter
    protected String name;
    @Getter@Setter
    protected int population;

    protected boolean possible = true;
    protected boolean potential = true;

    public Prefecture (String name_, int population_) {
        name = name_;
        population = population_;
    }

    public Prefecture() {

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

    @Override
    public int compareTo(Prefecture otherPrf) {
        return getName().compareTo(otherPrf.getName());
    }
}