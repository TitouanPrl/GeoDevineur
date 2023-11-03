package com.example.geodevineur;

import com.example.geodevineur.controller.EntityController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Format {

    @Autowired
    EntityController entityController;

    public Format(EntityController entityController_){
        this.entityController = entityController_;
    }

    //Fonction servant à mettre en forme un entier en mettant des espaces tous les 3 chiffres
    //exemple : 156832 => "156 832"
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

    //Fonction servant à renvoyer un lien vers une donnée de la BDD (pref/dep/reg) sur la page apprendre
    public StringBuilder getLinkOf(String name){
        StringBuilder htmlContent = new StringBuilder();
        htmlContent.append("<a class=\"\" href=\"http://localhost:8080/apprendre?type=").append(entityController.getTypeByName(name)).append("&name=").append(name).append("\">").append(name).append("</a>");
        return htmlContent;
    }

    //Fonction servant à determiner le genre d'un mot (masculin, feminin, pluriel)
    //PAS TERMINé
    public String getGenreByName(String name, String letter ){ //letter=d ou l
        String result = ""; //la, les
        String last_char = name.substring(name.length() - 1);
        if(last_char.equals("s")){
            return letter+"es";
        } else if(last_char.equals("e") && letter.equals("l")){
            return "la";
        } else {
            return "le";
        }
    }

    //Fonction servant à arrondir un decimal (value) à X chiffres apres la virgules (ici X = places)
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

    //Fonction contenant la formule de calcul du score
    public static int calculScore(int secondes, int nb_questions){
        int max = 10000;
        int a = 100;
        int b = 50;
        return (max - a * nb_questions - b * secondes);
    }
}
