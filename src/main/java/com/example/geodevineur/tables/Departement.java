package com.example.geodevineur.tables;

import com.example.geodevineur.enumerations.*;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
public class Departement implements Comparable<Departement> {

    @Id@Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int id;

    @Getter@Setter
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Region.class)
    @JoinColumn(name = "region_id")
    protected Region region;

    @Getter@Setter
    @OneToOne(mappedBy = "departement", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    protected Prefecture prefecture;

    @Getter@Setter
    protected String name;
    @Getter@Setter
    protected String number; //"01" -> "2A" -> "95";
    @Getter@Setter
    protected int population;
    @Getter@Setter
    protected double surface;
    @Getter@Setter
    protected boolean seaside;
    @Getter@Setter
    protected int neightbours;
    @Getter@Setter
    protected Politic politic;

    @Getter@Setter
    protected boolean possible = true;
    @Getter@Setter
    protected boolean potential = true;

    public Departement (String name_,
                        String number_,
                        int pop_,
                        double surf_,
                        boolean sea_,
                        int neigh_,
                        Politic politic_) {
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

    @Override
    public int compareTo(Departement otherDpt) {
        return getName().compareTo(otherDpt.getName());
    }

    public Cardinal getCardinal(){
        return getRegion().getCardinal();
    }
}
