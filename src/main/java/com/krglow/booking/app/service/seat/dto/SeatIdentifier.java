package com.krglow.booking.app.service.seat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
public class SeatIdentifier {

    private Long rowNumber;
    private Long seatNumber;

}
