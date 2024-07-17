package com.example.ScooterModel;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@Getter
@Setter
public class ScooterModel {
    private long id;

    private double latitude;

    private double longitude;

    private int chargePercentage;

    private boolean isAvailable;
}
