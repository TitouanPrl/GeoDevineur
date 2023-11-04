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

    /* Resets the map to its original state */
    public void clear() throws IOException {
        this.content = Files.readString(Path.of(this.filePath));
    }

    /* Colours a department on the map */
    public void colorizeDepartement(Departement departement, String color){
        String cible = "_" + departement.getNumber().toLowerCase() + "\"";
        this.content = this.content.replace(cible, cible + " style=\"fill: " + color + ";\"");
    }

    /* Colours several department on the map */
    public void colorizeDepartements(List<Departement> departements, String color) {
        departements.forEach(departement -> colorizeDepartement(departement,color));
    }

    /* Colours a region on the map */
    public void colorizeRegion(Region region, String color){
        colorizeDepartements(region.getDepartements(), color);
    }

    /* Place a dot representing a prefecture on the map */
    public void colorizePrefecture(Prefecture prefecture, String color){
        int[] location = getPrefectureLocation(prefecture);
        this.content = this.content.replace("#fill", color);
        this.content = this.content.replace("valueX", String.valueOf(location[0]));
        this.content = this.content.replace("valueY", String.valueOf(location[1]));
    }

    /* Returns X and Y coordinates of a prefecture according to the csv */
    public int[] getPrefectureLocation(Prefecture prefecture){
        int x = 0;
        int y = 0;
        String filePathCoordonnees = "src/main/resources/static/csv/coordonnees.csv";
        try (BufferedReader brP = new BufferedReader(new FileReader(filePathCoordonnees))) {
            String line;
            line = brP.readLine(); /* Jumping first line */
            while ((line = brP.readLine()) != null) {
                /* Creating an array using the separator to set the cases */
                String[] values = line.split(",");
                /* Looking for the given city */
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
