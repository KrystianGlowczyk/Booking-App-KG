package com.krglow.booking.app.service.reservation.exception;

public class InvalidVoucherCodeException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public InvalidVoucherCodeException(String message) {
        super(message);
    }
}
