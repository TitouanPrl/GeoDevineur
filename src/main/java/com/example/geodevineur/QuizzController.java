package com.example.geodevineur;

import com.example.geodevineur.tables.Departement;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Time;
import java.time.Duration;
import java.time.Instant;

public class QuizzController {



    public class Cond{

    }

    @Getter@Setter
    private Instant startTime;


    public String playing(){

        return "quizz";
    }

    public void changeQuestion(String question){


    }

    public Cond titouan(Departement departement){
        Cond result = null;

        return result;
    }

    public void start(){
        setStartTime(Instant.now());
        Departement toFind;
        //get1stQuestion
    }

    public void end(){
        int seconds = (int) Duration.between(Instant.now(),getStartTime()).toSeconds();
    }


}
