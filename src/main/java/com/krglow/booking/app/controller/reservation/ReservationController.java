package com.krglow.booking.app.controller.reservation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.krglow.booking.app.service.reservation.ReservationService;
import com.krglow.booking.app.service.reservation.dto.ReservationRequest;
import com.krglow.booking.app.service.reservation.dto.ReservationResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;


@RestController
@AllArgsConstructor
@RequestMapping("/reservations")
@Tag(name = "Kontroler rezerwacji", description = "Operacje związane z rezerwacjami")
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    @Operation(summary = "Tworzy nową rezerwację")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pomyślnie stworzono rezerwację", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ReservationResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "Nieprawidłowe dane żądania", content = @Content),
            @ApiResponse(responseCode = "404", description = "Nie znaleziono rezerwacji", content = @Content),
            @ApiResponse(responseCode = "409", description = "Konflikt danych", content = @Content), })
    public ResponseEntity<ReservationResponse> createReservation(@Parameter(description = "Parametry rezerwacji", required = true) @RequestBody ReservationRequest reservationRequest) {
        return ResponseEntity.ok(reservationService.createReservation(reservationRequest));
    }

    @PostMapping("/{id}/confirmation")
    @Operation(summary = "Potwierdza rezerwację o danym ID")
    public ResponseEntity<String> confirmReservation(@Parameter(description = "ID rezerwacji do potwierdzenia", required = true) @PathVariable Long id) {
        reservationService.confirmReservation(id);
        return ResponseEntity.ok("Potwierdzenie rejestracji zakończone powodzeniem!");
    }

    @GetMapping("/confirm")
    @Operation(summary = "Potwierdza rezerwację za pomocą tokenu")
    public ResponseEntity<String> confirmReservation(@Parameter(description = "Token rezerwacji do potwierdzenia", required = true) @RequestParam("token") String token) {
        reservationService.confirmReservation(token);
        return ResponseEntity.ok("Rezerwacja potwierdzona!");
    }

}
