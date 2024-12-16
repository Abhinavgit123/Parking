package com.parking.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Resource not found");
    }


    @ExceptionHandler(InvalidTicketException.class)
    public ResponseEntity<Map<String,Object>> handleInvalidTickets(InvalidTicketException ex) {
        Map<String,Object> map=new HashMap<>();
        map.put("message", ex.getMessage());
        map.put("errorCode", ex.getErrorCode());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
    }

    @ExceptionHandler(ParkingSpotException.class)
    public ResponseEntity<Map<String,Object>> handleUnavailableParkingSpot(ParkingSpotException ex) {
        Map<String,Object> map=new HashMap<>();
        map.put("message", ex.getMessage());
        map.put("errorCode", ex.getErrorCode());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
    }
@ExceptionHandler(PaymentException.class)
    public ResponseEntity<Map<String,Object>> handleInvalidPaymentMethod(PaymentException ex) {
        Map<String,Object> map=new HashMap<>();
        map.put("message", ex.getMessage());
        map.put("errorCode", ex.getErrorCode());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
    }

    @ExceptionHandler(VehicleTypeException.class)
    public ResponseEntity<Map<String,Object>> handleInvalidVehicleType(VehicleTypeException ex) {
        Map<String,Object> map=new HashMap<>();
        map.put("message", ex.getMessage());
        map.put("errorCode", ex.getErrorCode());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
    }


}
