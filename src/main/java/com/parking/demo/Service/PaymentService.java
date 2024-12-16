package com.parking.demo.Service;

import com.parking.demo.Entity.ParkingTicket;
import com.parking.demo.Entity.Payment;
import com.parking.demo.Entity.PaymentMethod;
import com.parking.demo.Entity.PaymentStatus;
import com.parking.demo.Repository.ParkingTicketRepository;
import com.parking.demo.Repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final ParkingTicketRepository parkingTicketRepository;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository, ParkingTicketRepository parkingTicketRepository) {
        this.paymentRepository = paymentRepository;
        this.parkingTicketRepository = parkingTicketRepository;
    }

    /**
     * Process a payment for a given parking ticket.
     *
     * @param ticketId      ID of the parking ticket
     * @param paymentMethod Payment method (e.g., CASH, CREDIT_CARD)
     * @param amount        Payment amount
     * @return Payment object after processing
     */
    public Payment processPayment(Long ticketId, PaymentMethod paymentMethod, Double amount) {
        // Fetch the parking ticket
        ParkingTicket ticket = parkingTicketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Parking ticket not found"));

        // Check if the payment has already been made
        if (ticket.getPaymentStatus() == PaymentStatus.COMPLETED) {
            throw new RuntimeException("Payment for this ticket has already been completed.");
        }

        // Calculate the amount due if needed (logic can be enhanced as required)
        Double amountDue = calculateAmountDue(ticket);
        if (!amount.equals(amountDue)) {
            throw new RuntimeException("Payment amount does not match the due amount. Due: " + amountDue);
        }

        // Create and save the payment record
        Payment payment = new Payment();
        payment.setPaymentMethod(paymentMethod);
        payment.setAmount(amount);
        payment.setParkingTicket(ticket);
        paymentRepository.save(payment);

        // Update the ticket's payment status
        ticket.setPaymentStatus(PaymentStatus.COMPLETED);
        parkingTicketRepository.save(ticket);

        return payment;
    }

    /**
     * Calculates the amount due for the parking ticket.
     *
     * @param ticket ParkingTicket object
     * @return Amount due for the ticket
     */
    private Double calculateAmountDue(ParkingTicket ticket) {
        if (ticket.getEntryTime() == null || ticket.getExitTime() == null) {
            throw new RuntimeException("Cannot calculate payment due: Entry or Exit time is missing.");
        }

        // Example calculation: $10 per hour
        long hoursParked = java.time.Duration.between(ticket.getEntryTime(), ticket.getExitTime()).toHours();
        return hoursParked * 10.0; // Example rate
    }
}
