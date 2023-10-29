package com.example.geodevineur.tables;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;

import java.sql.Time;

import jakarta.persistence.Entity;

/* Classe représentant les scores du leaderboard */
@Entity
public class Score {

    @Id
    @Getter
    private final String pseudo;
    @Getter
    private final Time temps;

    public Score(String pseudo_, Time temps_) {
        pseudo = pseudo_;
        temps = temps_;
    }

    public Score() {
        temps = null;
        pseudo = null;
    }
}
