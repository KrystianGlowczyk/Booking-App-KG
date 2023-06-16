package com.krglow.booking.app.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.krglow.booking.app.entity.screening.ScreeningEntity;
import com.krglow.booking.app.service.TicketType;
import com.krglow.booking.app.service.screening.dto.ScreeningResponse;
import com.krglow.booking.app.service.seat.dto.SeatDto;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ScreeningUtil {

    public static ScreeningResponse toScreeningReadDto(ScreeningEntity entity, List<SeatDto> availableSeats, List<SeatDto> seats) {
        ScreeningResponse dto = new ScreeningResponse();
        dto.setId(entity.getId());
        dto.setMovieTitle(entity.getMovie().getTitle());
        dto.setRoom(entity.getScreeningRoom().getRoomName());
        dto.setSeats(seatMatrix(seats));
        dto.setAvailableSeats(seatMatrix(availableSeats));
        dto.setTicketTypes(getTicketTypes());
        return dto;
    }

    private static List<TicketType> getTicketTypes() {
        return Arrays.asList(TicketType.values());
    }

    private static List<List<String>> seatMatrix(List<SeatDto> seats) {
        List<List<String>> seatMatrix = new ArrayList<>();
        Map<Long, List<SeatDto>> seatsByRow = seats.stream().collect(Collectors.groupingBy(SeatDto::getRowNumber));
        List<Long> sortedRows = new ArrayList<>(seatsByRow.keySet());
        Collections.sort(sortedRows);

        for (Long row : sortedRows) {
            List<String> seatRow = seatsByRow.get(row).stream().map(seat -> seat.getRowNumber().toString() + "-" + seat.getNumber().toString()).toList();
            seatMatrix.add(seatRow);
        }

        return seatMatrix;
    }

}
