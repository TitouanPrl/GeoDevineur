package com.example.geodevineur;

import com.example.geodevineur.controllers.ScoreController;
import com.example.geodevineur.tables.Score;
import netscape.javascript.JSObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ScorePage {

    @Autowired
    ScoreController scoreController;

    public ScorePage(ScoreController scoreController_){
        this.scoreController = scoreController_;
    }

    @RequestMapping(value = "scores", params = {"pseudo","password","seconds","nb"})
    public String checkInfos(@RequestParam String pseudo, @RequestParam String password, @RequestParam String seconds, @RequestParam String nb){
        String status = scoreController.proceed(pseudo, password, Integer.parseInt(seconds), Integer.parseInt(nb));
        System.out.println("status = "+status);
        //set argument like : return "score?status="+status;
        return "redirect:/scores?status="+status;
    }

    @RequestMapping(value = "scores")
    public String scores(Model model){
        List<Score> allScores = scoreController.getAll();
        StringBuilder htmlContent = new StringBuilder();

        htmlContent.append("<ul style=\"border: 1px\">");
        for(Score score : allScores){
            htmlContent.append("<li>");
            htmlContent.append(score.getScore()).append(" | ").append(score.getPseudo()).append(" (").append(score.getVersion()).append(" tentatives)");
            htmlContent.append("<input type=\"button\" id=\"").append(score.getId()).append("\" value=\"Supprimer\">");
            htmlContent.append("</li>");
        }
        htmlContent.append("</ul>");
        model.addAttribute("allScores", htmlContent);
        return "scores";
    }

    public StringBuilder getPopUp(){
        StringBuilder htmlContent = new StringBuilder();

        return htmlContent;
    }
}
