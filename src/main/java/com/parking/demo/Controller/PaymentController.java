package com.parking.demo.Controller;

import com.parking.demo.Entity.Payment;
import com.parking.demo.Entity.PaymentMethod;
import com.parking.demo.Service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/process")
    public ResponseEntity<Payment> processPayment(@RequestParam Long ticketId,
                                                  @RequestParam PaymentMethod paymentMethod,
                                                  @RequestParam Double amount) {
        Payment payment = paymentService.processPayment(ticketId, paymentMethod, amount);
        return ResponseEntity.ok(payment);
    }
}

