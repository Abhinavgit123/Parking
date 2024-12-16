package com.parking.demo.Service;

import com.parking.demo.Entity.*;
import com.parking.demo.Repository.ParkingSpotRepository;
import com.parking.demo.Repository.ParkingTicketRepository;
import com.parking.demo.Repository.TicketRepository;
import com.parking.demo.Repository.VehicleRepository;
import com.parking.demo.dto.VehicleCountDTO;
import com.parking.demo.dto.VehicleRequest;
import com.parking.demo.exception.InvalidTicketException;
import com.parking.demo.exception.ParkingSpotException;
import com.parking.demo.exception.PaymentException;
import com.parking.demo.exception.VehicleTypeException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class ParkingService {

    private final VehicleRepository vehicleRepository;
    ChargesServiceImpl chargesService = new ChargesServiceImpl();

    private final ParkingTicketRepository parkingTicketRepository;
    private final ParkingSpotRepository parkingSpotRepository;
    private final TicketRepository ticketRepository;



    public ParkingService(VehicleRepository vehicleRepository, ParkingTicketRepository parkingTicketRepository, ParkingSpotRepository parkingSpotRepository, TicketRepository ticketRepository) {
        this.vehicleRepository = vehicleRepository;
        this.parkingTicketRepository = parkingTicketRepository;
        this.parkingSpotRepository = parkingSpotRepository;
        this.ticketRepository = ticketRepository;
    }

    public long getTotalVehicleCount(){
        return parkingTicketRepository.countActiveVehiclesParked();
    }

    public List<VehicleCountDTO> countVehiclesByTypeCurrentlyParked() {
        return parkingTicketRepository.countVehiclesByTypeCurrentlyParked();
    }


    public double unparkVehicle(String licensePlate) {
        Vehicle vehicle = vehicleRepository.findByLicensePlate(licensePlate);
        if (vehicle == null) {
            throw new IllegalStateException("Vehicle not found.");
        }

        ParkingTicket ticket = ticketRepository.findByVehicle(vehicle);
        if (ticket == null || !"ACTIVE".equals(ticket.getStatus())) {
            throw new IllegalStateException("No active ticket found for the vehicle.");
        }

        ticket.setExitTime(LocalDateTime.now());
        double parkingCharge=chargesService.calculateCharges(vehicle.getType(),ticket.getEntryTime(),ticket.getExitTime());
        ticket.setParkingCharge(parkingCharge);

        ticket.setPaymentStatus(PaymentStatus.COMPLETED);
        ticketRepository.save(ticket);

        ParkingSpot parkingSpot = ticket.getParkingSpot();
        parkingSpot.setOccupied(false);
        parkingSpot.setStatus(ParkingStatus.AVAILABLE);
        parkingSpotRepository.save(parkingSpot);

        return parkingCharge;

    }

    public ParkingTicket createTicket(VehicleRequest VehicleNew) throws Exception {
        String licensePlate = VehicleNew.getLicensePlate();
        String type=VehicleNew.getType();
        // Step 1: Check if the vehicle already exists based on license plate
        Vehicle vehicle = vehicleRepository.findByLicensePlate(licensePlate);
        String spotType = null;
        if (vehicle == null) {
            // Create and save a new vehicle if it doesn't exist
            vehicle = new Vehicle();
            vehicle.setLicensePlate(licensePlate);


            if (type == null) {
                throw new VehicleTypeException("Vehicle type cannot be null",1002);
            }

            switch (type.toUpperCase()) {
                case "BIKE":
                    vehicle.setType(VehicleType.BIKE);
                    break;
                case "TRUCK":
                    vehicle.setType(VehicleType.TRUCK);
                    break;
                case "CAR":
                default:
                    vehicle.setType(VehicleType.CAR);
                    break;
            }
        }


        if ("BIKE".equalsIgnoreCase(String.valueOf(VehicleNew.getType()))) {
                spotType = "SMALL";
            }else if("TRUCK".equalsIgnoreCase(String.valueOf(VehicleNew.getType()))) {
                spotType = "LARGE";

            }else{
                spotType = "MEDIUM";
            }



        // Step 3: Find an available parking spot
        ParkingSpot parkingSpot = parkingSpotRepository.findFirstBySpotTypeAndOccupiedFalseOrderByIdAsc(spotType)
                .orElseThrow(() -> new ParkingSpotException("No parking spot available",1003));

        // Mark the parking spot as occupied
        parkingSpot.setOccupied(true);
        parkingSpot.setStatus(ParkingStatus.OCCUPIED);
        parkingSpotRepository.save(parkingSpot);

        vehicle.setParkingSpot(parkingSpot);
        vehicleRepository.save(vehicle);

        // Step 4: Create and save the ParkingTicket
        ParkingTicket ticket = new ParkingTicket();
        ticket.setVehicle(vehicle);
        ticket.setParkingSpot(parkingSpot);
        ticket.setEntryTime(LocalDateTime.now());
        ticket.setPaymentStatus(PaymentStatus.PENDING);
        ticket.setStatus("ACTIVE"); // Set any default status

        return parkingTicketRepository.save(ticket);
    }


    public void closeTicket(Long ticketId,PaymentMethod paymentMethod) {
        ParkingTicket ticket = parkingTicketRepository.findById(ticketId)
                .orElseThrow(() -> new InvalidTicketException("Ticket not found",1004));
        if (!Objects.equals(ticket.getStatus(), "ACTIVE")) {
            throw new InvalidTicketException("Invalid ticket status. Ticket must be active to close.",1004);
        }
        String licensePlate=ticket.getVehicle().getLicensePlate();
        double charge=unparkVehicle(licensePlate);
        ticket.setStatus("COMPLETED");


        PaymentStrategy paymentStrategy = getPaymentStrategy(paymentMethod);
        paymentStrategy.processPayment(charge);

        ticket.setPaymentStatus(PaymentStatus.COMPLETED);
        parkingTicketRepository.save(ticket);
    }

    private PaymentStrategy getPaymentStrategy(PaymentMethod paymentMethod) {
        if (paymentMethod == null) {
            throw new PaymentException("Invalid payment method", 1005);
        }
        switch (paymentMethod) {
            case CASH:
                return new CashPaymentStrategy();
            case UPI:
                return new UpiPaymentStrategy();
            default:
                throw new PaymentException("Invalid payment method", 1005);
        }
    }

}
