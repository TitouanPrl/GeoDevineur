package com.example.geodevineur;

import com.example.geodevineur.tables.Departement;
import com.example.geodevineur.tables.Prefecture;
import com.example.geodevineur.tables.Region;
import lombok.Getter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Map {

    String filePath;
    @Getter
    String content;

    public Map(String filePath_) throws IOException {
        this.filePath = filePath_;
        this.content = Files.readString(Path.of(filePath_));
    }

    //Procedure servant à reinitialiser la carte à son contenu d'origine (fichier)
    public void clear() throws IOException {
        this.content = Files.readString(Path.of(this.filePath));
    }

    //Procedure servant à colorer un departement sur la carte
    public void colorizeDepartement(Departement departement, String color){
        String cible = "_" + departement.getNumber().toLowerCase() + "\"";
        this.content = this.content.replace(cible, cible + " style=\"fill: " + color + ";\"");
    }

    //Procedure servant à colorer plusieurs departements sur la carte
    public void colorizeDepartements(List<Departement> departements, String color) {
        departements.forEach(departement -> colorizeDepartement(departement,color));
    }

    //Procedure servant à colorer une region (soit plusieurs departements) sur la carte
    public void colorizeRegion(Region region, String color){
        colorizeDepartements(region.getDepartements(), color);
    }

    //Procedure servant à placer le point d'une prefecture sur la carte en utilisant ses coordonnées
    public void colorizePrefecture(Prefecture prefecture, String color){
        int[] location = getPrefectureLocation(prefecture);
        this.content = this.content.replace("#fill", color);
        this.content = this.content.replace("valueX", String.valueOf(location[0]));
        this.content = this.content.replace("valueY", String.valueOf(location[1]));
    }

    //Retourne les coordonnees X et Y de la prefecture (stockées dans csv)
    public int[] getPrefectureLocation(Prefecture prefecture){
        int x = 0;
        int y = 0;
        String filePathCoordonnees = "src/main/resources/static/csv/coordonnees.csv";
        try (BufferedReader brP = new BufferedReader(new FileReader(filePathCoordonnees))) {
            String line;
            line = brP.readLine(); /* On saute la première ligne */
            while ((line = brP.readLine()) != null) {
                /* On crée un tableau en utilisant le séparateur pour séparer les cases */
                String[] values = line.split(",");
                /* On cherche la ville qui nous interesse */
                if(values[1].equals(prefecture.getName())){
                    x = Integer.parseInt(values[2]);
                    y = Integer.parseInt(values[3]);
                }
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
        return new int[] {x,y};
    }
}
