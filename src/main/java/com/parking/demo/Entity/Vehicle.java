package com.parking.demo.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private VehicleType type; // e.g., CAR, BIKE, TRUCK
    private String licensePlate;

    @ManyToOne
    private ParkingSpot parkingSpot;

    // Getters and setters
}


