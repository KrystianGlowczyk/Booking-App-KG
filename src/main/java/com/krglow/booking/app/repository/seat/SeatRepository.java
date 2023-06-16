package com.krglow.booking.app.repository.seat;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.krglow.booking.app.entity.reservation.ReservationEntity;
import com.krglow.booking.app.entity.screening.ScreeningEntity;
import com.krglow.booking.app.entity.seat.SeatEntity;


public interface SeatRepository extends JpaRepository<SeatEntity, Long> {

    List<SeatEntity> findByScreeningAndDeletedFalse(ScreeningEntity screeningEntity);

    Optional<SeatEntity> findByScreeningIdAndRowNumberAndNumberAndDeletedFalse(Long screeningId, Long rowNumber, Long number);

    List<SeatEntity> findByReservationAndDeletedFalse(ReservationEntity reservation);

    List<SeatEntity> findByScreeningIdAndRowNumberAndDeletedFalse(Long screeningId, Long rowNumber);

}
