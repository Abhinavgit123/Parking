package com.parking.demo.Entity;


public class UpiPaymentStrategy implements PaymentStrategy {
    @Override
    public void processPayment(double amount) {
        System.out.println("Processing UPI payment of $" + amount);

    }
}
