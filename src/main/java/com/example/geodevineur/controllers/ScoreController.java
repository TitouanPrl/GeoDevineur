package com.example.geodevineur.controllers;

import com.example.geodevineur.Format;
import com.example.geodevineur.repos.ScoreRepository;
import com.example.geodevineur.tables.Score;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class ScoreController {

    @Autowired
    ScoreRepository scoreService;

    public List<Score> getAll(){
        List<Score> result = new ArrayList<>();
        scoreService.findAll().forEach(result::add);
        //à voir pour sort par les meilleurs
        return result;
    }

    public Score getBest(){
        Score best = null;
        for(Score score : getAll()){
            if(best == null || score.getScore() > best.getScore()){
                best = score;
            }
        }
        return best;
    }

    public Score getById(Integer id){
        return scoreService.findById(id).orElse(null);
    }

    public Score getByName(String name){
        Score result = null;
        for(Score score : getAll()){
            if(score.getPseudo().equals(name)){
                result = score;
            }
        }
        return result;
    }

    public Boolean proceed(String pseudo, String password, Time temps, int nb_questions){
        System.out.println("pseudo:"+pseudo);
        System.out.println("pwd:"+password);
        System.out.println("seconds:"+temps.toString());
        System.out.println("nb:"+nb_questions);
        int score = Format.calculScore(temps, nb_questions);
        Score scoreOfPseudo = getByName(pseudo);
        boolean status = true;
        if(scoreOfPseudo != null){
            //Un score avec ce pseudo existe deja
            if(scoreOfPseudo.getPassword().equals(password)){
                //edit la valeur du scoreOfPseudo
                scoreOfPseudo.update(score);
            } else {
                status = false;
            }
        } else {
            add(new Score(pseudo,password,score));
        }
        return status;
    }

    public void add(Score score){
        System.out.println("dans ADD");
        System.out.println(score.getPseudo());
        System.out.println(score.getPassword());
        System.out.println(score.getScore());
        System.out.println("-------");

        scoreService.save(score);
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
