package com.example.geodevineur;

import com.example.geodevineur.tables.Departement;
import com.example.geodevineur.tables.Prefecture;
import com.example.geodevineur.tables.Region;
import lombok.Getter;

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
    public void clear() throws IOException {
        this.content = Files.readString(Path.of(this.filePath));
    }

    public void colorizeDepartement(Departement departement, String color){
        String cible = "_" + departement.getNumber().toLowerCase() + "\"";
        this.content = this.content.replace(cible, cible + " style=\"fill: " + color + ";\"");
    }

    public void colorizeDepartements(List<Departement> departements, String color) {
        departements.forEach(departement -> colorizeDepartement(departement,color));
    }

    public void colorizeRegion(Region region, String color){
        colorizeDepartements(region.getDepartements(), color);
    }

    public void colorizePrefecture(Prefecture prefecture, String color){

    }
}
