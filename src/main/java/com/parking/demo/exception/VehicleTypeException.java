package com.parking.demo.exception;

public class VehicleTypeException extends RuntimeException {
    private int errorCode;
    public VehicleTypeException(String message,int errorCode) {
        super(message);
        this.errorCode=errorCode;
    }
    public int getErrorCode() {
        return errorCode;
    }
}
