package com.example.geodevineur.dep_reg;

import com.example.geodevineur.enumerations.*;

import jakarta.persistence.Entity;

@Entity
public class Departement extends DepReg {

    public Departement (String name_,
                        String id_,
                        int pop_,
                        double surf_,
                        Cardinal card_,
                        boolean sea_,
                        int neigh_,
                        Politic politic_) {
        name = name_;
        id = id_;
        population = pop_;
        surface = surf_;
        cardinal = card_;
        seaside = sea_;
        neightbours = neigh_;
        politic = politic_;
    }

    public Departement() {

    }
}
