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

    @RequestMapping(value = "scores")
    public String scores(Model model){
        List<Score> allScores = scoreController.getAll();
        StringBuilder htmlContent = new StringBuilder();

        htmlContent.append("<table><caption>Meilleurs scores (Total ").append(allScores.size()).append(")<caption>");
        htmlContent.append("<tr><th>Pos</th><th>Nom</th><th>Score</th><th>Quizz </th><th></th>");

        int i=1;
        for(Score score : allScores){
            htmlContent.append("<tr>");
            switch(i){ //Notation differente pour les 3 premiers
                case 1: htmlContent.append("<td>1er</td>");break;
                case 2: htmlContent.append("<td>2nd</td>");break;
                case 3: htmlContent.append("<td>3ème</td>");break;
                default: htmlContent.append("<td>").append(i).append("</td>");break;
            }
            htmlContent.append("<td>").append(score.getPseudo()).append("</td>");
            htmlContent.append("<td>").append(score.getScore()).append("</td>");
            htmlContent.append("<td>").append(score.getVersion()).append("</td>");
            htmlContent.append("<td><input type=\"button\" class=\"btn btn-danger\" id=\"").append(score.getPseudo()).append("\" value=\"Supprimer\" onclick=\"deleteBtn(id)\"></td>");
            htmlContent.append("</tr>");
            i++;
        }
        htmlContent.append("</table>");
        model.addAttribute("allScores", htmlContent);
        return "scores";
    }

    //Check des infos pour ajouter/editer score apres fin quizz
    @RequestMapping(value = "scores", params = {"pseudo","password","seconds","nb"})
    public String checkInfos(@RequestParam String pseudo, @RequestParam String password, @RequestParam String seconds, @RequestParam String nb){
        String status = scoreController.proceed(pseudo, password, Integer.parseInt(seconds), Integer.parseInt(nb));
        System.out.println("status = "+status);
        return "redirect:/scores?status="+status;
    }

    @RequestMapping(value = "scores", params = {"pseudo", "password"})
    public String deleteScore(@RequestParam String pseudo, @RequestParam String password){
        Score score = scoreController.getByName(pseudo);
        String status = "Failure";
        if(score != null && score.getPassword().equals(password)){
            scoreController.deleteByPseudo(pseudo);
            status = "Success";
        }
        return "redirect:/scores?status=delete"+status;//succes-failure
    }
}
