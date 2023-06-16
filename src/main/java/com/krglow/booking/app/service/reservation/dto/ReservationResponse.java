package com.krglow.booking.app.service.reservation.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Schema(description = "Szczegóły odpowiedzi rezerwacji")
public class ReservationResponse {

    @Schema(description = "ID rezerwacji")
    private Long reservationId;

    @Schema(description = "Całkowita kwota rezerwacji")
    private BigDecimal totalAmount;

    @Schema(description = "Czas wygaśnięcia rezerwacji")
    private LocalDateTime expirationTime;

    @Schema(description = "Link do potwierdzenia rezerwacji")
    private String confirmationLink;

}
