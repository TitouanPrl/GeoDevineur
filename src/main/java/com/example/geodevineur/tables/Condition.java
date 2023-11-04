package com.example.geodevineur.tables;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
public class Condition{

    @Id
    protected int id;

    //enumratio pour type = {prefecture/region/departement}

    @Getter
    protected String attribut;
    @Getter
    protected String delta;

    public Condition (String attribut_, String delta_) {
        attribut = attribut_;
        delta = delta_;
    }

    public Condition() {

    }
}