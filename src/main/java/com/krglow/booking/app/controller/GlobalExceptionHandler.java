package com.krglow.booking.app.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.krglow.booking.app.service.reservation.exception.InvalidReservationException;
import com.krglow.booking.app.service.reservation.exception.InvalidVoucherCodeException;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFoundException(EntityNotFoundException ex) {
        log.error("Entity not found exception", ex);
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidReservationException.class)
    public ResponseEntity<String> handleInvalidReservationException(InvalidReservationException ex) {
        log.error("Invalid reservation exception", ex);
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidVoucherCodeException.class)
    public ResponseEntity<String> handleInvalidVoucherCodeException(InvalidVoucherCodeException ex) {
        log.error("Invalid voucher code exception", ex);
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception ex) {
        log.error("General exception", ex);
        return new ResponseEntity<>("Unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
