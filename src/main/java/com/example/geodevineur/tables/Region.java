package com.example.geodevineur.tables;

import com.example.geodevineur.enumerations.*;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
public class Region implements Comparable<Region> {

    @Id@Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int id;

    @Getter@Setter
    @OneToMany(mappedBy = "region", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    protected List<Departement> departements = new ArrayList<Departement>();

    @Getter@Setter
    protected String name;
    @Getter@Setter
    protected Cardinal cardinal;

    protected boolean possible = true;
    protected boolean potential = true;

    public Region (String name_, Cardinal card_) {
        name = name_;
        cardinal = card_;
    }

    public Region() {

    }

    public int getPopulation(){
        int total = 0;
        for(Departement departement : departements){
            total += departement.getPopulation();
        }
        return total;
    }

    public int getSurface(){
        double total = 0;
        for(Departement departement : departements){
            total += departement.getSurface();
        }
        return (int) total;
    }

    @Override
    public int compareTo(Region otherReg) {
        return getName().compareTo(otherReg.getName());
    }

}