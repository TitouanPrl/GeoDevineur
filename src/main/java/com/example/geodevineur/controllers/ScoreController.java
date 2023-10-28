package com.example.geodevineur.controllers;

import com.example.geodevineur.repos.ScoreRepository;
import com.example.geodevineur.tables.Score;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Controller
public class ScoreController {

    @Autowired
    ScoreRepository scoreService;

    public List<Score> getAll(){
        List<Score> result = new ArrayList<>();
        scoreService.findAll().forEach(result::add);
        //à voir pour sort par les meilleurs
        return result;
    }

    public Score getById(Integer id){
        return scoreService.findById(id).orElse(null);
    }

    public void add(String pseudo, Time temps){
        scoreService.save(new Score(pseudo,temps));
    }

    public Boolean delete(Integer id){
        Optional<Score> cible = scoreService.findById(id);
        cible.ifPresent(score -> scoreService.delete(score));
        return (cible.isPresent());
    }

    public void deleteAll(){
        scoreService.deleteAll();
    }

}
