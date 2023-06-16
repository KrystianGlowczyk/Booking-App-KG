package com.krglow.booking.app.service.seat;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.krglow.booking.app.entity.reservation.ReservationEntity;
import com.krglow.booking.app.entity.seat.SeatEntity;
import com.krglow.booking.app.repository.seat.SeatRepository;
import com.krglow.booking.app.service.reservation.dto.Ticket;
import com.krglow.booking.app.service.reservation.exception.InvalidReservationException;
import com.krglow.booking.app.service.seat.dto.SeatIdentifier;

import lombok.AllArgsConstructor;


@Service
@AllArgsConstructor
public class SeatServiceImpl implements SeatService {

    private final SeatRepository seatRepository;

    /**
     * Checks if there is no single seat left between the reserved seats.
     *
     * @param reservationEntity the reservation entity
     * @param tickets list of tickets
     * @return true if there is no single seat left
     * @throws InvalidReservationException if there's a single seat left between the reserved seats
     */
    @Override
    public boolean checkNoSingleSeatLeft(ReservationEntity reservationEntity, List<Ticket> tickets) {
        Set<Long> rowNumbers = getRowNumbersFromTickets(tickets);
        Map<String, SeatIdentifier> seatIdentifierMap = getSeatIdentifierMapFromTickets(tickets);

        for (Long rowNumber : rowNumbers) {
            List<SeatEntity> seatsInDb = getSortedSeatsFromDb(reservationEntity, rowNumber);

            updateSeatsReservationStatus(seatIdentifierMap, seatsInDb);
            checkForSingleEmptySeats(seatsInDb);
        }

        return true;
    }

    private Set<Long> getRowNumbersFromTickets(List<Ticket> tickets) {
        Set<Long> rowNumbers = new HashSet<>();
        for (Ticket ticket : tickets) {
            SeatIdentifier seatId = splitSeatIdentifier(ticket.getSeat());
            rowNumbers.add(seatId.getRowNumber());
        }
        return rowNumbers;
    }

    private Map<String, SeatIdentifier> getSeatIdentifierMapFromTickets(List<Ticket> tickets) {
        Map<String, SeatIdentifier> seatIdentifierMap = new HashMap<>();
        for (Ticket ticket : tickets) {
            SeatIdentifier seatId = splitSeatIdentifier(ticket.getSeat());
            seatIdentifierMap.put(ticket.getSeat(), seatId);
        }
        return seatIdentifierMap;
    }

    private List<SeatEntity> getSortedSeatsFromDb(ReservationEntity reservationEntity, Long rowNumber) {
        List<SeatEntity> seatsInDb = seatRepository.findByScreeningIdAndRowNumberAndDeletedFalse(reservationEntity.getScreening().getId(), rowNumber);
        seatsInDb.sort(Comparator.comparing(SeatEntity::getNumber));
        return seatsInDb;
    }

    private void updateSeatsReservationStatus(Map<String, SeatIdentifier> seatIdentifierMap, List<SeatEntity> seatsInDb) {
        for (SeatEntity seatEntity : seatsInDb) {
            String seatKey = seatEntity.getRowNumber() + "-" + seatEntity.getNumber();
            if (seatIdentifierMap.containsKey(seatKey)) {
                if (seatEntity.isReserved()) {
                    throw new InvalidReservationException("Miejsce: " + seatKey + " jest już zarezerwowane");
                } else {
                    seatEntity.setReserved(true);
                }
            }
        }
    }

    private void checkForSingleEmptySeats(List<SeatEntity> seatsInDb) {
        for (int i = 1; i < seatsInDb.size() - 1; i++) {
            SeatEntity leftSeat = seatsInDb.get(i - 1);
            SeatEntity middleSeat = seatsInDb.get(i);
            SeatEntity rightSeat = seatsInDb.get(i + 1);

            if (!middleSeat.isReserved() && leftSeat.isReserved() && rightSeat.isReserved()) {
                throw new InvalidReservationException(
                        "Nie może zostać puste miejsce między dwoma zajętymi miejscami. Miejsce: " + middleSeat.getRowNumber() + "-" + middleSeat.getNumber() + " jest wolne");
            }
        }
    }

    /**
     * Splits a seat identifier into a row number and a seat number.
     *
     * @param seatIdentifier the seat identifier
     * @return the seat identifier object
     */
    @Override
    public SeatIdentifier splitSeatIdentifier(String seatIdentifier) {
        String[] split = seatIdentifier.split("-");
        return new SeatIdentifier(Long.parseLong(split[0]), Long.parseLong(split[1]));
    }

}
