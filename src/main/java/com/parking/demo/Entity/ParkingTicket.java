package com.parking.demo.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
//@Data
public class ParkingTicket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Vehicle vehicle;
    private double parkingCharge;
    private String status;
    private LocalDateTime entryTime;
    private LocalDateTime exitTime;

    @ManyToOne
    private ParkingSpot parkingSpot;



    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

//    public PaymentStatus getPaymentStatus() {
//        return paymentStatus;
//    }
//
//    public void setPaymentStatus(PaymentStatus paymentStatus) {
//        this.paymentStatus = paymentStatus;
//    }


    // Getters and setters
}

