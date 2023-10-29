package com.example.geodevineur.tables;

import com.example.geodevineur.enumerations.*;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
public class Condition {

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