package com.parking.demo.exception;

public class PaymentException extends RuntimeException{
    private int errorCode;
    public PaymentException(String message,int errorCode) {
        super(message);
        this.errorCode=errorCode;
    }

    public int getErrorCode(){
        return errorCode;
    }
}
