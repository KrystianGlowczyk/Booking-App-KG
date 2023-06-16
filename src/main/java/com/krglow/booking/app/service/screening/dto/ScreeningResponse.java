package com.krglow.booking.app.service.screening.dto;

import java.util.List;

import com.krglow.booking.app.service.TicketType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Schema(description = "Szczegóły odpowiedzi seansu")
public class ScreeningResponse {

    @Schema(description = "ID seansu")
    private Long id;

    @Schema(description = "Tytuł filmu")
    private String movieTitle;

    @Schema(description = "Sala seansu")
    private String room;

    @Schema(description = "Miejsca na sali")
    private List<List<String>> seats;

    @Schema(description = "Dostępne miejsca na sali")
    private List<List<String>> availableSeats;

    @Schema(description = "Typy biletów")
    private List<TicketType> ticketTypes;
}
