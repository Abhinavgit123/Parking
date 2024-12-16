package com.parking.demo.dto;

import com.parking.demo.Entity.VehicleType;
import lombok.Data;

@Data
public class VehicleCountDTO {
    private VehicleType vehicleType;
    private long countByType;

    public VehicleCountDTO(VehicleType vehicleType, long countByType) {
        this.vehicleType = vehicleType;
        this.countByType = countByType;
    }

    // Getters and Setters
}

