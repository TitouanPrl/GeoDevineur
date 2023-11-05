package com.example.geodevineur.controllers;

import com.example.geodevineur.Format;
import com.example.geodevineur.repos.ScoreRepository;
import com.example.geodevineur.tables.Score;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ScoreController {

    @Autowired
    ScoreRepository scoreService;

    /* Returns a list of all the scores */
    public List<Score> getAll(){
        List<Score> result = new ArrayList<>();
        scoreService.findAll().forEach(result::add);
        Collections.sort(result);
        Collections.reverse(result);
        return result;
    }

    /* Returns the best score */
    public Score getBest(){
        Score best = null;
        for(Score score : getAll()){
            if(best == null || score.getScore() > best.getScore()){
                best = score;
            }
        }
        return best;
    }

    /* Returns a score by its id */
    public Score getById(Integer id){
        return scoreService.findById(id).orElse(null);
    }

    /* Returns a score by its player name */
    public Score getByName(String name){
        Score result = null;
        for(Score score : getAll()){
            if(score.getPseudo().equals(name)){
                result = score;
            }
        }
        return result;
    }

    /* Add a score to the db, or update it if the pseudo already exists */
    public String proceed(String pseudo, String password, int secondes, int nb_questions){
        int score = Format.calculScore(secondes, nb_questions);

        Score scoreOfPseudo = getByName(pseudo);
        String status;
        /* Checking if a score with this pseudo already exists */
        if(scoreOfPseudo != null){
            /* If the password matches, we edit the score */
            if(scoreOfPseudo.getPassword().equals(password)){
                update(scoreOfPseudo, score);
                status = "edited";
            } else {
                status = "error";
            }
        } else {
            status = "added";
            add(new Score(pseudo,password,score));
        }
        return status;
    }

    public void add(Score score){
        scoreService.save(score);
    }

    public void update(Score score, int new_score){
        score.update(new_score);
        deleteById(score.getId());
        scoreService.save(score);
        //ICI CHANGER PAR MODIFIER
    }

    /* Deletes a score by its id */
    public Boolean deleteById(Integer id){
        Optional<Score> cible = scoreService.findById(id);
        cible.ifPresent(score -> scoreService.delete(score));
        return (cible.isPresent());
    }

    /* Deletes a score by its pseudo */
    public Boolean deleteByPseudo(String pseudo){
        List<Score> allScores = getAll();
        boolean found = false;
        for(Score score : allScores){
            if(score.getPseudo().equals(pseudo)){
                scoreService.delete(score);
                found = true;
            }
        }
        return found;
    }

    public void deleteAll(){
        scoreService.deleteAll();
    }
}
