package org.example;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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

    private AreaCorner areaLowerCorner;

    private AreaCorner areaUpperCorner;

    private int criticalChargeValue;

    private int stationsAmount;

    private int scootersAmount;

    private int gridSize;

    private int batteryLimit;

    private boolean randomGeneration;

    private int checkPeriodInSec;

    private String loggerFilename;

    @Getter
    @Setter
    public static class AreaCorner {
        private double lat;
        private double lon;
    }
}
