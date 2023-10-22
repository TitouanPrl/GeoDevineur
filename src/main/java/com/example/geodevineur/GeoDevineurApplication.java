package com.example.geodevineur;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages={
"com.example.geodevineur.dep_reg", "com.example.geodevineur.enumerations", "com.example.geodevineur.DepartementRepository"})
public class GeoDevineurApplication {

    public static void main(String[] args) {

        SpringApplication.run(GeoDevineurApplication.class, args);
    }

}