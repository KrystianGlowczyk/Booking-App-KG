package com.krglow.booking.app.service.screening;

import java.util.List;

import org.springframework.stereotype.Service;

import com.krglow.booking.app.entity.screening.ScreeningEntity;
import com.krglow.booking.app.entity.seat.SeatEntity;
import com.krglow.booking.app.repository.screening.ScreeningRepository;
import com.krglow.booking.app.repository.seat.SeatRepository;
import com.krglow.booking.app.service.screening.dto.ScreeningResponse;
import com.krglow.booking.app.service.seat.dto.SeatDto;
import com.krglow.booking.app.util.ScreeningUtil;
import com.krglow.booking.app.util.SeatUtil;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;


/**
 * Implementation of the ScreeningService.
 */
@Slf4j
@Service
@AllArgsConstructor
public class ScreeningServiceImpl implements ScreeningService {

    private final ScreeningRepository screeningRepository;
    private final SeatRepository seatRepository;

    /**
     * Gets the screening info.
     *
     * @param screeningId the id of the screening
     * @return the screening info
     * @throws EntityNotFoundException if the screening is not found
     */
    @Override
    public ScreeningResponse getScreeningInfo(Long screeningId) {

        ScreeningEntity screening = screeningRepository.findById(screeningId).orElseThrow(() -> {
            log.error("Screening not found for id: {}", screeningId);
            return new EntityNotFoundException("Nie znaleziono seansu");
        });

        return ScreeningUtil.toScreeningReadDto(screening, getAvailableSeats(screeningId), screening.getSeats().stream().map(SeatUtil::toSeatDto).toList());

    }

    /**
     * Gets the available seats for a given screening.
     *
     * @param screeningId the id of the screening
     * @return the list of available seats
     * @throws EntityNotFoundException if the screening is not found
     */
    @Override
    public List<SeatDto> getAvailableSeats(Long screeningId) {

        ScreeningEntity screeningEntity = screeningRepository.findById(screeningId).orElseThrow(() -> {
            log.error("Screening not found for id: {}", screeningId);
            return new EntityNotFoundException();
        });

        List<SeatEntity> allSeats = seatRepository.findByScreeningAndDeletedFalse(screeningEntity);
        List<SeatEntity> availableSeatEntities = allSeats.stream().filter(seat -> !seat.isReserved()).toList();

        return availableSeatEntities.stream().map(SeatUtil::toSeatDto).toList();
    }

}
