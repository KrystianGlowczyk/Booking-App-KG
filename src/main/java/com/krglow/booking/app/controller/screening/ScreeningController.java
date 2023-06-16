package com.krglow.booking.app.controller.screening;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.krglow.booking.app.service.screening.ScreeningService;
import com.krglow.booking.app.service.screening.dto.ScreeningResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
@RequestMapping("/screenings")
@AllArgsConstructor
@Tag(name = "Kontroler seansów", description = "Operacje związane z seansami")
public class ScreeningController {

    private final ScreeningService screeningService;

    @GetMapping("/{id}")
    @Operation(summary = "Zwraca informacje o seansie o danym ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pomyślnie znaleziono seans", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ScreeningResponse.class)) }),
            @ApiResponse(responseCode = "404", description = "Nie znaleziono seansu", content = @Content), @ApiResponse(responseCode = "500", description = "Błąd serwera", content = @Content) })
    public ResponseEntity<ScreeningResponse> getScreeningInfo(@Parameter(description = "ID seansu do wyszukania", required = true) @PathVariable Long id) {
        log.info("Processing request for screening info for id: {}", id);
        ScreeningResponse screening = screeningService.getScreeningInfo(id);
        log.info("Screening info for id: {} successfully fetched", id);
        return ResponseEntity.ok(screening);
    }

}
