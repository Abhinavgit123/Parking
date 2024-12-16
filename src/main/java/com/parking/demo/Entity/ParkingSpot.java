package com.parking.demo.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class ParkingSpot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String spotNumber;
    private String spotType; // SMALL, MEDIUM, LARGE

    @Enumerated(EnumType.STRING)
    private ParkingStatus status;

    private boolean occupied;

//    @OneToOne(mappedBy = "parkingSpot")
//    private Vehicle vehicle;

    // Getters and setters
}
