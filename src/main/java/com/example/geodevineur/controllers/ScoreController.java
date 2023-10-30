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
            if(score.getName().equals(name)){
                result = score;
            }
        }
        return result;
    }

    public Boolean proceed(String pseudo, String password, Time temps, int nb_questions){
        int score = Format.calculScore(temps, nb_questions);
        Score scoreOfPseudo = getByName(pseudo);
        boolean status = true;
        if(scoreOfPseudo != null){
            //Un score avec ce pseudo existe deja
            if(scoreOfPseudo.getPassword().equals(password)){
                //edit la valeur du scoreOfPseudo
                update(scoreOfPseudo, score);
            } else {
                status = false;
            }
        } else {
            add(pseudo, password, score);
        }
        return status;
    }

    public void add(String pseudo, String password, int score){
        scoreService.save(new Score(pseudo,password,score));
    }

    public void update(Score scoreToEdit, int new_score){
        //On calcule la nouvelle moyenne des scores
        //int final_score = (scoreToEdit.getScore()*scoreToEdit.getVersion() + new_score) / (scoreToEdit.getVersion()+1);
        //On ajoute le nouveau score à l'ancien
        scoreToEdit.setScore(scoreToEdit.getScore()+new_score);
        scoreToEdit.setVersion(scoreToEdit.getVersion()+1);
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
