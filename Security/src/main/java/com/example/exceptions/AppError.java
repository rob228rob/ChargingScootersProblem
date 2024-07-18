package com.example.exceptions;

import lombok.Data;

import java.util.Date;

@Data
public class AppError {

    private int errorCode;
    private String errorMessage;
    private Date timestamp;

    public AppError(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.timestamp = new Date();
    }
}
