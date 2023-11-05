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
    @Autowired
    Format format;

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
    private List<String> previousAnswers;

    public QuizzController(DepartementController departementController_, ConditionController conditionController_, Format format_){
        this.departementController = departementController_;
        this.conditionController = conditionController_;
        this.format = format_;
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
        /* Sets empty previous answers */
        setPreviousAnswers(new ArrayList<>());
        /* Set the list of conditions for the quizz, according to the searched department */
        setConditions(conditionController.getRun(departementController.getAll(), getDepartementToFind()));
        return "redirect:/quizz?nextQ=start";
    }

    /* Main fonction, verify if the solution is found and if no, set the next question */
    @RequestMapping(value = "quizz", params = "nextQ")
    public String nextQ(Model model, @RequestParam String nextQ){
        /* Response written by the player */
        Departement departementOfInput = getDepartementFromStringInput(nextQ);

        /* Comparison with the target (without the special chars) */
        if(getStep() > 0 && departementOfInput != null && departementOfInput.getName().equals(getDepartementToFind().getName())) {
            int seconds = (int) Duration.between(getStartTime(),Instant.now()).toSeconds();
            model.addAttribute("scoreModal", getScoreModal(seconds));
            return "quizz";
        }

        /* Getting the last condition, null if 1st question */
        Condition<Departement> previousCond = null;
        if (getStep() > 0)
            previousCond = getConditions().get(getStep()-1);

        /* Checking if the department written matches the condition */
        if(previousCond == null || (departementOfInput != null && previousCond.checksCondition(departementOfInput))) {
            /* No previous questions if no previous conds, no need to print*/
            if(previousCond != null){
                previousAnswers.add(previousCond.getSentence() + " : " + departementOfInput.getName());
                model.addAttribute("previousQuestions",getPreviousQuestions());
            }
            model.addAttribute("questionContent",getTemplate(getConditions().get(getStep()).getSentence()));
            setStep(getStep()+1);
        } else {
            String errorSentence = "";
            if(departementOfInput != null)
                errorSentence = getErrorCorrection(departementOfInput, previousCond);
            model.addAttribute("scoreModal",getLoseModal(errorSentence));
        }
        return "quizz";
    }

    /* Gets a department from a string and deletes its special chars */
    public Departement getDepartementFromStringInput(String input){
        String name = format.clearString(input);
        Departement cible = null;
        for(Departement departement : departementController.getAll()){
            if(format.clearString(departement.getName()).equals(name)){
                cible = departement;
            }
        }
        return cible;
    }

    /*----------------------------ALL-TEMPLATES--------------------------------------*/

    /* Returns html template of all the previous questions */
    public StringBuilder getPreviousQuestions(){
        StringBuilder htmlContent = new StringBuilder();
        htmlContent.append("<h4>Questions répondues : </h4>");
        htmlContent.append("<ul>");
        for(String line : getPreviousAnswers()){
            htmlContent.append("<li>").append(line).append("</li>");
        }
        htmlContent.append("</ul>");
        return htmlContent;
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
        if (getStep() > 0) template.append("<p class=\"card-text\">").append(getPreviousQuestions()).append("</p>");
        template.append("<form action=\"quizz?nextQ=true\" method=\"get\" class=\"align-items-center text-center\">");
        template.append("<input  autocomplete=\"off\" class=\"\" type=\"text\" name=\"nextQ\"><br><br>");
        template.append("<input class=\"btn\" type=\"submit\" value=\"Valider\"><br><br>");
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
        htmlContent.append("<i>Temps : <b>").append(format.getTimeStringFromSeconds(seconds)).append("</b>, Nombre de questions : <b>").append(getStep()).append("</b><br><br></i>");
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
    public StringBuilder getLoseModal(String errorSentence){
        StringBuilder htmlContent = new StringBuilder();
        htmlContent.append("<div class=\"modal\" id=\"formScoreModal\" style=\"display: block;\">");
        htmlContent.append("<div class=\"modal-content\">");
        htmlContent.append("<span class=\"close\" id=\"closeModal\" onclick=\"closeModal()\">&times;</span>");
        htmlContent.append("<h2>Dommage vous avez perdu ...</h2><br>");
        htmlContent.append("<h4>").append(errorSentence).append("</h4><br>");
        htmlContent.append("<h4>La réponse était : ").append(getDepartementToFind().getName()).append("</h4>");
        htmlContent.append("</div>");
        htmlContent.append("</div>");
        return htmlContent;
    }

    /* Return the losing reason and the correction */
    public String getErrorCorrection(Departement mistakeInputed, Condition<Departement> losingCond){
        if(losingCond.getSentence().contains("situe au")){ //Cardinal
            return mistakeInputed.getName() + " se situe au " + mistakeInputed.getCardinal() + " de la France (pas " + departementToFind.getCardinal() + ")";
        } else if(losingCond.getSentence().contains("lettres")){ //NBcharacters
            return mistakeInputed.getName() + " contient " + mistakeInputed.getName().length() + " lettres (pas " + departementToFind.getName().length() + ")";
        } else if(losingCond.getSentence().contains("voisins")){ //Neightbours
            return mistakeInputed.getName() + " possède " + mistakeInputed.getNeightbours() + " voisins (pas " + departementToFind.getNeightbours() + ")";
        } else if(losingCond.getSentence().contains("numéro")){ //Number
            return mistakeInputed.getName() + " a pour numéro le " + mistakeInputed.getNumber();
        } else if(losingCond.getSentence().contains("vote")){ //Politic
            return mistakeInputed.getName() + " vote " + mistakeInputed.getPolitic() + " en majorité (pas " + departementToFind.getPolitic() + ")";
        } else if(losingCond.getSentence().contains("habitants")){ //Population
            return mistakeInputed.getName() + " a " + Format.intToFormatedString(mistakeInputed.getPopulation()) + " habitants (pas " + Format.intToFormatedString(departementToFind.getPopulation()) + ")";
        } else if(losingCond.getSentence().contains("préfecture")){ //Prefecture
            return mistakeInputed.getName() + " a pour préfecture " + mistakeInputed.getPrefecture().getName() + " (pas " + departementToFind.getPrefecture().getName() + ")";
        } else if(losingCond.getSentence().contains("région")){ //Region
            return mistakeInputed.getName() + " a pour région " + mistakeInputed.getRegion().getName() + " (pas " + departementToFind.getRegion().getName() + ")";
        } else if(losingCond.getSentence().contains("mer")){ //Seaside
            if(mistakeInputed.getSeaside()) return mistakeInputed.getName() + " se situe en bord de mer";
            else return mistakeInputed.getName() + " ne se situe pas en bord de mer";
        } else if(losingCond.getSentence().contains("km")){ //Surface
            return mistakeInputed.getName() + " compte " + mistakeInputed.getSurface() + " km² (pas " + departementToFind.getSurface() + ")";
        } else {
            return "";
        }
    }

    /* Returns html template for the button launching the quizz */
    public StringBuilder getStartButton(){
        return new StringBuilder("<button class=\"button-start\" id=\"start\" role=\"button\"><span class=\"text\">Démarrer le Quizz</span></button>");
    }
}
