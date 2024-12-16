package com.parking.demo.exception;

public class ParkingSpotException extends Exception{
    private int errorCode;
    public ParkingSpotException(String message,int errorCode) {
        super(message);
        this.errorCode=errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }

}
