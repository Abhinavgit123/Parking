package com.parking.demo.Entity;

public class CashPaymentStrategy implements PaymentStrategy {

    @Override
    public void processPayment(double amount) {
        System.out.println("Processing cash payment of " + amount);
    }
}
