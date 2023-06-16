package com.krglow.booking.app.service.reservation.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Schema(description = "Szczegóły żądania rezerwacji")
public class ReservationRequest {

    @Schema(description = "ID seansu")
    private Long screeningId;

    @Schema(description = "Lista miejsc")
    private List<Ticket> seats;

    @Schema(description = "Imię osoby rezerwującej")
    private String name;

    @Schema(description = "Nazwisko osoby rezerwującej")
    private String surname;

    @Schema(description = "Email osoby rezerwującej")
    private String email;

    @Schema(description = "Kod vouchera zniżkowego")
    private String voucher;

}
