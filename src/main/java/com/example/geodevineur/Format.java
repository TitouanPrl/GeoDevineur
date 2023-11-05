package com.example.geodevineur;

import com.example.geodevineur.controllers.EntityController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Format {

    @Autowired
    EntityController entityController;

    public Format(EntityController entityController_){
        this.entityController = entityController_;
    }

    /* Formats a number with spaces
     * Exemple : 156832 => "156 832"*/
    public static String intToFormatedString(int nombre) {
        String nombreStr = Integer.toString(nombre);
        StringBuilder resultat = new StringBuilder();
        for (int i = nombreStr.length() - 1, count = 0; i >= 0; i--) {
            resultat.insert(0, nombreStr.charAt(i));
            count++;
            if (count % 3 == 0 && i > 0) {
                resultat.insert(0, ' ');
            }
        }
        return resultat.toString();
    }

    /* Returns a link to a db data on learning page */
    public StringBuilder getLinkOf(String name){
        StringBuilder htmlContent = new StringBuilder();
        htmlContent.append("<a class=\"\" href=\"http://localhost:8080/apprendre?type=").append(entityController.getTypeByName(name)).append("&name=").append(name).append("\">").append(name).append("</a>");
        return htmlContent;
    }

    /* Clear a string by deleting {,|'| |-}, accents, and turning capitals into small letters */
    public String clearString(String value){
        value = value.replace("é","e").replace("è","e").replace("î","i").replace("Î","i").replace("ô","o");
        return value.replace(" ","").replace("-","").replace("'","").replace(",","").toLowerCase();
    }

    //PAS TERMINé
    /* Finding the gender of a word */
    public String getPronom(String name, String letter ){ //letter=d ou l
        String result = ""; //la, les
        String last_char = name.substring(name.length() - 1);
        String first_char = name.substring(0,0);
        if(last_char.equals("s")){
            return letter+"es";
        } else if(last_char.equals("e") && letter.equals("l")){
            return "la";
        } else {
            return "le";
        }
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

    /* Set the number of decimals of a float to "places" */
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    public static int roundInt(int number, int step){
        int result = 0;
        while(number > step){
            result++;
            number -= step;
        }
        if(number > step/2) result++;
        return result*step;
    }

    public static Boolean IntToBoolean(int x){
        return (x == 1);
    }

    /* Calculates a score */
    public static int calculScore(int secondes, int nb_questions){
        int max = 10000;
        int a = 100;
        int b = 200;
        int score = max - a * nb_questions - b * secondes;
        return Math.max(score, 0);
    }
}
