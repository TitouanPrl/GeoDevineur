package com.example.geodevineur;

import com.example.geodevineur.controllers.ScoreController;
import netscape.javascript.JSObject;
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

    @RequestMapping(value = "addScore", params = {"pseudo","password","seconds","nb"})
    public String checkInfos(@RequestParam String pseudo, @RequestParam String password, @RequestParam String seconds, @RequestParam String nb){
        System.out.println("ici dans checkInfos");

        int nb_questions = Integer.parseInt(nb);
        int time = Integer.parseInt(seconds);

        boolean status = scoreController.proceed(pseudo, password, new Time(0,0,time), nb_questions);
        System.out.println("status = "+status);
        //set argument like : return "score?status="+status;
        return "scores";
    }

    public StringBuilder getPopUp(){
        StringBuilder htmlContent = new StringBuilder();

        return htmlContent;
    }
}
