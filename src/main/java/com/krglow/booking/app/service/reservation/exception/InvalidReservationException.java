package com.krglow.booking.app.service.reservation.exception;

public class InvalidReservationException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public InvalidReservationException(String message) {
        super(message);
    }

}
