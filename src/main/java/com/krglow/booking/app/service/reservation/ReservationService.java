package com.krglow.booking.app.service.reservation;

import com.krglow.booking.app.service.reservation.dto.ReservationRequest;
import com.krglow.booking.app.service.reservation.dto.ReservationResponse;


public interface ReservationService {

    ReservationResponse createReservation(ReservationRequest reservation);

    boolean confirmReservation(Long reservationId);

    int cancelReservations();

    boolean confirmReservation(String token);

}
