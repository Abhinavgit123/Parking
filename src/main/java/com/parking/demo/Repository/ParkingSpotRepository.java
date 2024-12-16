package com.parking.demo.Repository;

import com.parking.demo.Entity.ParkingSpot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ParkingSpotRepository extends JpaRepository<ParkingSpot, String> {
    ParkingSpot findFirstByStatus(String status);
//    @Query("SELECT ps FROM ParkingSpot ps WHERE ps.spotType = :spotType AND ps.occupied = false ORDER BY ps.id ASC")
//    Optional<ParkingSpot> findNextAvailableSpot(@Param("spotType") String spotType);

    Optional<ParkingSpot> findFirstBySpotTypeAndOccupiedFalseOrderByIdAsc(String spotType);

}

