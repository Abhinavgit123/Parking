package com.parking.demo.dto;

import lombok.Data;

@Data
public class VehicleRequest {
    private String licensePlate;
    private String type; // Accept as a string in the request

    // Getters and setters
}

