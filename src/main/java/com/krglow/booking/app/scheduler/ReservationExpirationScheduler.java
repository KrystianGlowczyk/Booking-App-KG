package com.krglow.booking.app.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.krglow.booking.app.service.reservation.ReservationService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component
@AllArgsConstructor
public class ReservationExpirationScheduler {

    private final ReservationService reservationService;

    @Scheduled(fixedRate = 60000)
    public void cancelExpiredReservations() {
        log.info("Starting task to cancel expired reservations...");
        try {
            int count = reservationService.cancelReservations();
            log.info("Task completed. {} reservations have been cancelled.", count);
        } catch (Exception e) {
            log.error("An error occurred during the task execution.", e);
        }
    }

}
