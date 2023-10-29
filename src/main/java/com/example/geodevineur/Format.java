package com.example.geodevineur;

public class Format {

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

    public static StringBuilder link(String name){
        StringBuilder htmlContent = new StringBuilder();
        htmlContent.append("<a href=\"http://localhost:8080/apprendre?nom=").append(name).append("\">").append(name).append("</a>");
        return htmlContent;
    }
}
