package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties
@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        //AppConfig appConfig = new AppConfig();
        //System.out.println(appConfig.getAreaLowerCorner() +" " + appConfig.getScootersAmount());
        SpringApplication.run(Main.class, args);
    }
}