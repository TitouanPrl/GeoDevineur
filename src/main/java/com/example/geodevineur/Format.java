package com.example.geodevineur;

import com.example.geodevineur.controllers.EntityController;
import com.example.geodevineur.tables.Departement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Component
public class Format {

    @Autowired
    EntityController entityController;

    public Format(EntityController entityController_){
        this.entityController = entityController_;
    }

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

    public StringBuilder getLinkOf(String name){
        StringBuilder htmlContent = new StringBuilder();
        htmlContent.append("<a class=\"\" href=\"http://localhost:8080/apprendre?type=").append(entityController.getTypeByName(name)).append("&name=").append(name).append("\">").append(name).append("</a>");
        return htmlContent;
    }

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

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    public static Boolean IntToBoolean(int x){
        return (x == 1);
    }
}
