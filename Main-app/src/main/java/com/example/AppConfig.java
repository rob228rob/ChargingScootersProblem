package com.example;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app")
@Getter
@Setter
@NoArgsConstructor
public class AppConfig {

    private double latUpperCorner;

    private double lonUpperCorner;

    private double latLowerCorner;

    private double lonLowerCorner;

    private AreaCorner areaLowerCorner;

    private AreaCorner areaUpperCorner;

    @PostConstruct
    public void init()
    {
        System.out.println(stationsAmount + " - stations amount;\n");
        areaLowerCorner = new AreaCorner(latLowerCorner, lonLowerCorner);
        areaUpperCorner = new AreaCorner(latUpperCorner, lonUpperCorner);
    }

    private int criticalChargeValue;

    private int stationsAmount;

    private int scootersAmount;

    private int gridSize;

    private int batteryLimit;

    private boolean needRandomGeneration;

    private int checkPeriodInSec;

    private String loggerFilename;

    @Getter
    @Setter
    @AllArgsConstructor
    public static class AreaCorner {
        private double lat;
        private double lon;
    }
}
