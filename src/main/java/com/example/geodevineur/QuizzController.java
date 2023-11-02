package com.example.geodevineur;

import com.example.geodevineur.controllers.DepartementController;
import com.example.geodevineur.tables.Departement;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    private String status; //waiting-running   //a delete plus tard
    @Getter@Setter
    private Departement departementToFind;
    @Getter@Setter
    private int step;
    @Getter@Setter
    private int score; //a delete plus tard

    public QuizzController(DepartementController departementController_){
        this.departementController = departementController_;
        this.status = "waiting";
    }

    //Lien de redirection en cas d'absence d'arguments
    @RequestMapping(value = "quizz")
    public String redirect() {
        return "redirect:/quizz?waiting=true";
    }

    //Affichage du bouton pour lancer le quizz si la page est en status waiting
    @RequestMapping(value = "quizz", params = "waiting")
    public String waiting(Model model) {
        model.addAttribute("startButton", getStartButton());
        return "quizz";
    }

    //Initialisation du quizz (temps, cible, status, etape)
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

    //Fonction principale appelée lors d'un clic sur Valider
    //Elle verifie si la reponse est trouvée et sinon calcule la question suivante pour l'afficher 
    @RequestMapping(value = "quizz", params = "nextQ")
    public String nextQ(Model model, @RequestParam String nextQ){
        //On compare la reponse avec la cible, en epurant les expressions (minuscule+pas d'accents+pas de '-',' ',''')
        if(clearString(nextQ).equals(clearString(departementToFind.getName()))){
            //Le departement est trouvé
            int seconds = end();
            model.addAttribute("scoreModal",getScoreModal(seconds));
        } else {
            setStep(getStep()+1);
            //Bail de conditions ici
            StringBuilder question = getQuestion();
            model.addAttribute("questionContent",question);
        }
        return "quizz";
    }

    //Declenché quand cible trouvé, met à jour le status et retourne la durée du quizz en secondes
    public int end(){
        setStatus("finished");
        return (int) Duration.between(getStartTime(),Instant.now()).toSeconds();
    }

    //Epure un chaine de caracteres en enlevant les {,|'| |-} les accents et transforme les majuscules en minuscules
    public String clearString(String value){
        return value.replace(" ","").replace("-","").replace("'","").replace(",","").toLowerCase();
    }

    //Renvoie la question suivante
    public StringBuilder getQuestion(){
        return getTemplate("Le departement commence par un C");
    }

    //Renvoie le template html de la question
    public StringBuilder getTemplate(String question){
        StringBuilder template = new StringBuilder();
        template.append("<div class=\"card mb-4 box-shadow\">");
        template.append("<div class=\"question card-title align-items-center bg-success text-center\">");
        template.append("<h3 class=\"border-bottom border-dark \"><strong>Question n°").append(getStep()).append("</strong></h3>");
        template.append("<h1>").append(question).append("</h1>");
        template.append("</div>");
        template.append("<div class=\"card-body align-items-center\">");
        template.append("<form action=\"quizz?nextQ=true\" method=\"get\" class=\"align-items-center text-center\">");
        template.append("<input class=\"\" type=\"text\" name=\"nextQ\"><br><br>");
        template.append("<input class=\"btn\" type=\"submit\" value=\"Valider\"><br><br>");
        //template.append("<i class=\"card-subtitle border-top\">43 correspondants</i>");
        template.append("</form>");
        template.append("</div>");
        template.append("</div>");
        return template;
    }

    //Renvoie le template html de la modale de saisie de score (quand quizz terminé)
    public StringBuilder getScoreModal(int seconds){
        StringBuilder htmlContent = new StringBuilder();
        htmlContent.append("<div class=\"modal\" id=\"formScoreModal\" style=\"display: block;\">");
        htmlContent.append("<div class=\"modal-content\">");
        htmlContent.append("<span class=\"close\" id=\"closeModal\" onclick=\"closeModal()\">&times;</span>");
        htmlContent.append("<h2>Bien joué, vous avez fait un score de <b>").append(Format.calculScore(seconds,getStep())).append("</b></h2>");
        htmlContent.append("<i>Temps : <b>").append(getTimeStringFromSeconds(seconds)).append("</b>, Nombre de questions : <b>").append(getStep()).append("</b><br><br></i>");
        htmlContent.append("<form id=\"saveScore\" onsubmit=\"proceedForm()\">");
        htmlContent.append("<div class=\"form-group\">");
        htmlContent.append("<label for=\"pseudo\">Pseudo</label>");
        htmlContent.append("<input type=\"text\" class=\"form-control\" id=\"pseudo\" aria-describedby=\"emailHelp\" placeholder=\"Saisissez votre pseudo\" required>");
        htmlContent.append("</div>");
        htmlContent.append("<div class=\"form-group\">");
        htmlContent.append("<label for=\"password\">Password</label>");
        htmlContent.append("<input type=\"text\" class=\"form-control\" id=\"password\" placeholder=\"Saisissez votre code confidentiel\">");
        htmlContent.append("</div>");
        htmlContent.append("<input type=\"hidden\" id=\"seconds\" value=\"").append(seconds).append("\">");
        htmlContent.append("<input type=\"hidden\" id=\"nb\" value=\"").append(getStep()).append("\">");
        htmlContent.append("<button type=\"submit\" class=\"btn btn-primary\">Enregistrer</button>");
        htmlContent.append("</form>");
        htmlContent.append("</div>");
        htmlContent.append("</div>");
        return htmlContent;
    }

    //Renvoie une chaine de caracteres avec des min/sec depuis des secondes (int)
    public String getTimeStringFromSeconds(int seconds){
        int minutes = 0;
        String sentence;
        while (seconds > 60){
            minutes++;
            seconds -= 60;
        }
        if(minutes == 0){
            sentence = seconds+" secondes";
        } else {
            sentence = minutes+"min "+seconds+"secondes";
        }
        return sentence;
    }

    //Renvoie le template html du bouton de lancement du quizz
    public StringBuilder getStartButton(){
        return new StringBuilder("<button class=\"btn btn-success\" id=\"start\">DEMARRER</button>");
    }

    //Procedure temporaire pour afficher les infos du quizz au moment de l'appel
    public void getQuizzStatus(){
        System.out.println("------QUIZZ-STATUS------");
        System.out.println("status="+getStatus());
        System.out.println("step="+getStep());
        System.out.println("depToFind="+getDepartementToFind().getName());
        System.out.println("time="+getStartTime());
        System.out.println("------------------------");
    }
}
