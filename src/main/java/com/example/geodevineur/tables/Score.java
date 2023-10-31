package com.example.geodevineur.tables;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;

@Entity
public class Score {

    @Id@Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int id;

    @Getter@Setter
    protected String pseudo;
    @Getter@Setter
    protected String password;
    @Getter@Setter
    protected int score;
    @Getter@Setter
    protected int version;

    public Score(String pseudo_, String password_, int score_) {
        pseudo = pseudo_;
        password = password_;
        score = score_;
        version = 1;
    }

    public Score() {

    }

    public void update(int score){
        setScore(getScore()+score);
        setVersion(getVersion()+1);
    }

    public Boolean isValidPassword(String password){
        return (getPassword().equals(password));
    }

}