package com.example.ScootersRepository;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "scooters")
@Getter
@Setter
@NoArgsConstructor
public class ScooterEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false, unique = true)
    private Long id;

    @Column(name = "latitude")
    private double latitude;

    @Column(name = "longitude")
    private double longitude;

    @Column(name = "percentage")
    private int chargePercentage;

    @Column(name = "available")
    private boolean available;

    @Column(name = "changed_recently")
    private boolean changedRecently;
}
