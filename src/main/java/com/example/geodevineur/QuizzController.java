package com.example.geodevineur;

import com.example.geodevineur.condition.Condition;
import com.example.geodevineur.controllers.ConditionController;
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
import java.util.ArrayList;
import java.util.List;

@Controller
public class QuizzController {

    @Autowired
    DepartementController departementController;
    @Autowired
    ConditionController conditionController;

    @Getter@Setter
    public List<Condition<Departement>> conditions;
    @Getter@Setter
    public List<Departement> allDepartements;
    @Getter@Setter
    private Instant startTime;
    @Getter@Setter
    private Departement departementToFind;
    @Getter@Setter
    private int step;
    @Getter@Setter
    private int score; //a delete plus tard

    public QuizzController(DepartementController departementController_, ConditionController conditionController_){
        this.departementController = departementController_;
        this.conditionController = conditionController_;
        this.conditions = new ArrayList<>();
    }

    /* Redirects if no arguments */
    @RequestMapping(value = "quizz")
    public String redirect() {
        return "redirect:/quizz?waiting=true";
    }

    /* Prints starting button if page in waiting status */
    @RequestMapping(value = "quizz", params = "waiting")
    public String waiting(Model model) {
        //conditionController.printAllCondsOfDep(departementController.getByName("Tarn"));
        model.addAttribute("startButton", getStartButton());
        return "quizz";
    }

    /* Initializes the quizz (time, target, status, step) */
    @RequestMapping(value = "quizz", params = "start")
    public String start(){
        /* Sets the timer to 0 */
        setStartTime(Instant.now());
        /* Sets randomly the department to find */
        setDepartementToFind(departementController.getRandomOne());
        /* Sets the step counter to 0 */
        setStep(0);
        /* Set the list of conditions for the quizz, according to the searched department */
        setConditions(conditionController.getRun(departementController.getAll(), getDepartementToFind()));
        //Logs du departements à trouver A DELETE
        System.out.println("Departement to find : "+getDepartementToFind().getName());
        return "redirect:/quizz?nextQ=start";
    }

    //LOGS A DELTE !!
    /* Main fonction, verify if the solution is found and if no, set the next question */
    @RequestMapping(value = "quizz", params = "nextQ")
    public String nextQ(Model model, @RequestParam String nextQ){
        /* Comparing response and target (without the special chars) */
        System.out.println("-----------");
        System.out.println("Saisie : "+nextQ);

        /* Response written by the player */
        Departement departementOfInput = getDepartementFromSInput(nextQ);

        /* Comparison with the target */
        if(getStep() > 0 && departementOfInput != null && departementOfInput.getName().equals(getDepartementToFind().getName())) {
            int seconds = (int) Duration.between(getStartTime(),Instant.now()).toSeconds();
            model.addAttribute("scoreModal", getScoreModal(seconds));
            return "quizz";
        } else if (getStep() > 0 && departementOfInput != null){
            System.out.println(departementOfInput.getName()+" != "+departementToFind.getName());
        }

        /* Getting the last condition */
        Condition<Departement> previousCond = null;
        if (getStep() > 0) previousCond = getConditions().get(getStep()-1);

        /* Checking if the department written matches the condition */
        if(previousCond == null || (departementOfInput != null && previousCond.checksCondition(departementOfInput))) {
            //logs à delete
            if(previousCond != null)
                System.out.println(nextQ + " juste checked : "+previousCond.getSentence());

            model.addAttribute("questionContent",getTemplate(getConditions().get(getStep()).getSentence()));
            setStep(getStep()+1);
        } else {
            /* Lose, printing the end message */
            System.out.println(nextQ + (departementOfInput) + " didnt checked : "+previousCond.getSentence());
            model.addAttribute("scoreModal",getLoseModal());
        }

        return "quizz";
    }

    /* Gets a department from a string and deletes its special chars */
    public Departement getDepartementFromSInput(String input){
        String name = clearString(input);
        Departement cible = null;
        for(Departement departement : departementController.getAll()){
            if(clearString(departement.getName()).equals(name)){
                cible = departement;
            }
        }
        return cible;
    }

    /* Clear a string by deleting {,|'| |-}, accents, and turning capitals into small letters */
    public String clearString(String value){
        value = value.replace("é","e").replace("è","e").replace("î","i").replace("Î","i").replace("ô","o");
        return value.replace(" ","").replace("-","").replace("'","").replace(",","").toLowerCase();
    }

    /* Returns html template of the question */
    public StringBuilder getTemplate(String question){
        StringBuilder template = new StringBuilder();
        template.append("<div class=\"card mb-4 box-shadow\">");
        template.append("<div class=\"question card-title align-items-center bg-success text-center\">");
        template.append("<h3 class=\"border-bottom border-dark \"><strong>Question n°").append(getStep()+1  ).append("</strong></h3>");
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

    /* Returns html template of the score interface */
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

    /* Returns the html template for the lost interface */
    public StringBuilder getLoseModal(){
        StringBuilder htmlContent = new StringBuilder();
        htmlContent.append("<div class=\"modal\" id=\"formScoreModal\" style=\"display: block;\">");
        htmlContent.append("<div class=\"modal-content\">");
        htmlContent.append("<span class=\"close\" id=\"closeModal\" onclick=\"closeModal()\">&times;</span>");
        htmlContent.append("<h2>Dommage vous avez perdu ...</h2>");
        htmlContent.append("</div>");
        htmlContent.append("</div>");
        return htmlContent;
    }

    /* Converts seconds from int to string */
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

    /* Returns html template for the button launching the quizz */
    public StringBuilder getStartButton(){
        return new StringBuilder("<button class=\"btn btn-success btn-lg\" id=\"start\">DEMARRER</button>");
    }
}
