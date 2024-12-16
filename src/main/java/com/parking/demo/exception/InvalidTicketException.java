package com.parking.demo.exception;

public class InvalidTicketException extends RuntimeException {

    private int errorCode;

    public InvalidTicketException(String message, int errorCode) {
        super(message);
        this.errorCode=errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
