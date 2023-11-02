package com.example.geodevineur;

import com.example.geodevineur.controllers.DepartementController;
import com.example.geodevineur.tables.Departement;
import jdk.jfr.Category;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.yaml.snakeyaml.scanner.ScannerImpl;

import java.sql.Time;
import java.time.Duration;
import java.time.Instant;

@Controller
public class QuizzController {

    public class Cond{

    }

    @Autowired
    DepartementController departementController;

    @Getter@Setter
    private Instant startTime;
    @Getter@Setter
    private String status; //waiting-running
    @Getter@Setter
    private Departement departementToFind;
    @Getter@Setter
    private int step;

    public QuizzController(DepartementController departementController_){
        this.departementController = departementController_;
        this.status = "waiting";
    }

    public Cond titouan(Departement departement){
        Cond result = null;

        return result;
    }


    @RequestMapping(value = "quizz", params = "waiting")
    public String waiting(Model model) {
        model.addAttribute("startButton", getStartButton());
        return "quizz";
    }

    @RequestMapping(value = "quizz", params = "start")
    public String start(){
        //Mise à 0 du chrono de durée du quizz
        setStartTime(Instant.now());
        //Changement statut du quizz à en cours
        setStatus("running");
        //TIrage au sort du departement à trouver
        setDepartementToFind(departementController.getRandomOne());
        setStep(0);
        getQuizzStatus();
        return "redirect:/quizz?nextQ=true";
    }

    @RequestMapping(value = "quizz", params = "nextQ")
    public String nextQ(Model model){
        setStep(getStep()+1);
        System.out.println("In nextQ, step="+getStep());

        StringBuilder question = getQuestion();
        System.out.println(question);
        model.addAttribute("questionContent",question);

        return "quizz";
    }

    public StringBuilder getQuestion(){
        return getTemplate("Le departement commence par un C");
    }

//    <!-- bg-primary => bleu | bg-success => vert | bg-warning => rouge -->\n" +
//    <!-- Quand répondu : changement couleur + attribut 'disabled' au bouton du form + -->\n" +
//    <!-- valeur de la saisie texte correspond à reponse + nombre de departement correspondants dans balise i -->\n"
    public StringBuilder getTemplate(String question){
        StringBuilder template = new StringBuilder();
        template.append("<div class=\"card mb-4 box-shadow\">");
        template.append("<div class=\"question card-title align-items-center bg-success text-center\">");
        template.append("<h3 class=\"border-bottom border-dark \"><strong>Question n°1</strong></h3>");
        template.append("<h1>").append(question).append("</h1>");
        template.append("</div>");
        template.append("<div class=\"card-body align-items-center\">");
        template.append("<form action=\"quizz.html\" method=\"get\" class=\"align-items-center text-center\">");
        template.append("<input class=\"\" type=\"text\" name=\"answer\" value=\"Landes\"><br><br>");
        template.append("<i class=\"card-subtitle border-top\">43 correspondants</i>");
        template.append("</form>");
        template.append("</div>");
        template.append("</div>");
        return template;
    }

    public StringBuilder getStartButton(){
        return new StringBuilder("<button class=\"btn btn-success\" id=\"start\">DEMARRER</button>");
    }

    public void end(){
        int seconds = (int) Duration.between(Instant.now(),getStartTime()).toSeconds();
    }

    public void getQuizzStatus(){
        System.out.println("------QUIZZ-STATUS------");
        System.out.println("status="+getStatus());
        System.out.println("step="+getStep());
        System.out.println("depToFind="+getDepartementToFind().getName());
        System.out.println();
        System.out.println("------------------------");

    }
}
