package com.parking.demo.Repository;

import com.parking.demo.Entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface VehicleRepository extends JpaRepository<Vehicle, UUID> {
    Vehicle findByLicensePlate(String licensePlate);


}
