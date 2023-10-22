package com.example.geodevineur.dep_reg;

import com.example.geodevineur.enumerations.*;

public class Region extends DepReg{

    public Region (String name_, int pop_, double surf_, Cardinal card_, boolean sea_, int neigh_, String id_, Politic politic_) {
        name = name_;
        population = pop_;
        surface = surf_;
        cardinal = card_;
        seaside = sea_;
        neightbours = neigh_;
        id = id_;
        politic = politic_;
    }

}
