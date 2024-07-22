package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableConfigurationProperties
@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        //AppConfig appConfig = new AppConfig();
        //System.out.println(appConfig.getAreaLowerCorner() +" " + appConfig.getScootersAmount());
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String password = "100";
        System.out.println(encoder.encode(password));

        SpringApplication.run(Main.class, args);
    }
}