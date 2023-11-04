package com.example.geodevineur.tables;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
public class Score implements Comparable<Score>{

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
        System.out.println("---avant---");
        System.out.println(getPseudo());
        System.out.println(getScore());
        System.out.println(getVersion());
        System.out.println("------------");
        setScore(getScore()+score);
        setVersion(getVersion()+1);
        System.out.println("---apres---");
        System.out.println(getPseudo());
        System.out.println(getScore());
        System.out.println(getVersion());
        System.out.println("------------");

    }

    public Boolean isValidPassword(String password){
        return (getPassword().equals(password));
    }

    @Override
    public int compareTo(Score otherScore) {
        return Integer.compare(getScore(), otherScore.getScore());
    }
}