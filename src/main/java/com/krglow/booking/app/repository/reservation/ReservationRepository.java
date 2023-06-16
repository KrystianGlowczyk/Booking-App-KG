package com.krglow.booking.app.repository.reservation;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.krglow.booking.app.entity.reservation.ReservationEntity;


public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {

    List<ReservationEntity> findAllByExpirationTimeBeforeAndConfirmedFalseAndDeletedFalse(LocalDateTime expirationTime);

    Optional<ReservationEntity> findByConfirmationToken(String confirmationToken);

}
