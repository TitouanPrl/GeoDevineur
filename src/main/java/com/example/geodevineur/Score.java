package com.example.geodevineur;

import java.sql.Time;

import jakarta.persistence.Entity;

/* Classe représentant les scores du leaderboard */
@Entity
public class Score {
    private final String pseudo;
    private final Time temps;

    public Score (String pseudo_, Time temps_) {
        pseudo = pseudo_;
        temps = temps_;
    }


    public String getPseudo() {
        return pseudo;
    }

    public Time getTime() {
        return temps;
    }
}
