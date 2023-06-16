package com.krglow.booking.app.service.screening;

import java.util.List;

import com.krglow.booking.app.service.screening.dto.ScreeningResponse;
import com.krglow.booking.app.service.seat.dto.SeatDto;


public interface ScreeningService {

    ScreeningResponse getScreeningInfo(Long screeningId);

    List<SeatDto> getAvailableSeats(Long screeningId);

}
