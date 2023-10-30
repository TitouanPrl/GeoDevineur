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
    protected String name;
    @Getter@Setter
    protected String password;
    @Getter@Setter
    protected int score;
    @Getter@Setter
    protected int version;

    public Score(String name_, String password_, int score_) {
        name = name_;
        password = password_;
        score = score_;
        version = 1;
    }

    public Score() {

    }

    public Boolean isValidPassword(String password){
        return (getPassword().equals(password));
    }

}