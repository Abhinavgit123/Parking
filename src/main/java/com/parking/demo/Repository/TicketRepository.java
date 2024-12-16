package com.parking.demo.Repository;

import com.parking.demo.Entity.ParkingTicket;
import com.parking.demo.Entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TicketRepository extends JpaRepository<ParkingTicket, UUID> {
    ParkingTicket findByVehicle(Vehicle vehicle);
}
