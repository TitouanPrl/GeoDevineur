package com.example.geodevineur.tables;

import com.example.geodevineur.enumerations.*;

import java.util.List;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
public class Region {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Getter
    protected int id;

    @OneToMany
    @JoinColumn(name = "departement_ids")
    protected List<Departement> departements;

    @Getter
    protected String name;

    @Getter
    protected Cardinal cardinal;

    protected boolean possible = true;
    protected boolean potential = true;

    public Region (List<Departement> departements_, String name_, Cardinal card_) {
        departements = departements_;
        name = name_;
        cardinal = card_;
    }

    public Region() {

    }


}