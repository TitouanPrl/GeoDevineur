package com.example.geodevineur;

import com.example.geodevineur.controllers.ScoreController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Time;

@Controller
public class ScorePage {

    @Autowired
    ScoreController scoreController;

    public ScorePage(ScoreController scoreController_){
        this.scoreController = scoreController_;
    }

    @PostMapping("addScore")
    public String checkInfos(){
        System.out.println("ici dans checkInfos");
//        System.out.println("pseudo:"+pseudo);
//        System.out.println("pwd:"+password);
//        System.out.println("score:"+score);
//
//        int scoreValue = Integer.parseInt(score);

        String pseudo = "null";
        String password = "null";
        int nb_questions = 0;
        int seconds = 0;

        boolean status = scoreController.proceed(pseudo, password, new Time(0,0,seconds), nb_questions);
        //set argument like : return "score?status="+status;
        return "scores";
    }

    public StringBuilder getPopUp(){
        StringBuilder htmlContent = new StringBuilder();

        return htmlContent;
    }
}
