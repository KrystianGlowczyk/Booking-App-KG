package com.krglow.booking.app.util;

import com.krglow.booking.app.entity.seat.SeatEntity;
import com.krglow.booking.app.service.seat.dto.SeatDto;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SeatUtil {

    public static SeatDto toSeatDto(SeatEntity entity) {
        SeatDto dto = new SeatDto();
        dto.setId(entity.getId());
        dto.setNumber(entity.getNumber());
        dto.setRowNumber(entity.getRowNumber());
        return dto;
    }
}
