package com.krglow.booking.app.service.seat;

import java.util.List;

import com.krglow.booking.app.entity.reservation.ReservationEntity;
import com.krglow.booking.app.service.reservation.dto.Ticket;
import com.krglow.booking.app.service.seat.dto.SeatIdentifier;


public interface SeatService {

    boolean checkNoSingleSeatLeft(ReservationEntity reservationEntity, List<Ticket> tickets);

    SeatIdentifier splitSeatIdentifier(String seatIdentifier);

}
