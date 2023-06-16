package com.krglow.booking.app.service.movie.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.format.annotation.DateTimeFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Schema(description = "Szczegóły dotyczące zwróconych filmów")
public class MovieRequest {

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Schema(description = "Dzień dla którego mają zostać zwrócone filmy")
    private LocalDate day;

    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    @Schema(description = "Godzina dla której mają zostać zwrócone filmy")
    private LocalTime time;

}
