package com.parking.demo.Repository;

import com.parking.demo.Entity.ParkingTicket;
import com.parking.demo.dto.VehicleCountDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ParkingTicketRepository extends JpaRepository<ParkingTicket, Long> {

    @Query("SELECT COUNT(pt) FROM ParkingTicket pt WHERE pt.status = 'ACTIVE' AND pt.exitTime IS NULL")
    long countActiveVehiclesParked();



//    @Query("SELECT pt.vehicle.type AS vehicleType, COUNT(pt) AS countByType " +
//            "FROM ParkingTicket pt " +
//            "WHERE pt.status = 'ACTIVE' AND pt.exitTime IS NULL " +
//            "GROUP BY pt.vehicle.type")
//    List<VehicleCountDTO> countVehiclesByTypeCurrentlyParked();
//
//    @Query(value = "select v.type,count(*) as  count from parking_ticket p join vehicle v on p.vehicle_id=v.id WHERE p.status = 'ACTIVE' AND p.exit_time IS NULL group by v.type", nativeQuery = true)
//    List<VehicleCountDTO> countVehiclesByTypeCurrentlyParked();

    @Query("SELECT new com.parking.demo.dto.VehicleCountDTO(v.type, COUNT(p)) " +
            "FROM ParkingTicket p JOIN p.vehicle v " +
            "WHERE p.status = 'ACTIVE' AND p.exitTime IS NULL " +
            "GROUP BY v.type")
    List<VehicleCountDTO> countVehiclesByTypeCurrentlyParked();

}

