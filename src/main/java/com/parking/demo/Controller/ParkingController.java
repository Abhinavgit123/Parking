package com.parking.demo.Controller;

import com.parking.demo.Entity.*;
import com.parking.demo.Entity.ParkingTicket;
import com.parking.demo.Service.ParkingService;
import com.parking.demo.dto.VehicleCountDTO;
import com.parking.demo.dto.VehicleRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/parking")
public class ParkingController {
    private final ParkingService parkingService;

    public ParkingController(ParkingService parkingService) {
        this.parkingService = parkingService;
    }

    @PostMapping("/create-ticket")
        public ResponseEntity<ParkingTicket> createTicket(@RequestBody VehicleRequest request) throws Exception {
        ParkingTicket ticket = parkingService.createTicket(request);
        return ResponseEntity.ok(ticket);
    }

    @PostMapping("/close-ticket/{ticketId}")
    public ResponseEntity<String> closeTicket(@PathVariable Long ticketId, @RequestParam(required = false) String paymentMethod) {
        PaymentMethod paymentMethodEnum;
        try {
            paymentMethodEnum = paymentMethod != null ? PaymentMethod.valueOf(paymentMethod) : PaymentMethod.CASH;
        } catch (IllegalArgumentException ex) {
            paymentMethodEnum = null; // Pass null or a default value if invalid
        }

        parkingService.closeTicket(ticketId, paymentMethodEnum);
        return ResponseEntity.ok("Ticket closed successfully");
    }

    @GetMapping("/active-vehicles")
    public ResponseEntity<Long> getTotalVehicleCount(){
        long count= parkingService.getTotalVehicleCount();
        return  ResponseEntity.ok(count);
    }

    @GetMapping("/total-vehicles-byType")
    public ResponseEntity<List<VehicleCountDTO>> getVehicleCountByType(){

        List<VehicleCountDTO> count=parkingService.countVehiclesByTypeCurrentlyParked();
       return ResponseEntity.ok(count);
    }
}
