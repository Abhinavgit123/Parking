package com.parking.demo.Entity;

import java.time.LocalDateTime;

public interface IChargesService {
    double calculateCharges(VehicleType vehicleType, LocalDateTime entryTime, LocalDateTime exitTime);
}
