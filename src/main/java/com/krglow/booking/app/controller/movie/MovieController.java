package com.krglow.booking.app.controller.movie;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.krglow.booking.app.entity.movie.VMovieEntity;
import com.krglow.booking.app.service.movie.MovieService;
import com.krglow.booking.app.service.movie.dto.MovieRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;


@RestController
@RequestMapping("/movies")
@AllArgsConstructor
@Tag(name = "Kontroler filmów", description = "Operacje związane z filmami")
public class MovieController {

    private final MovieService movieService;

    @GetMapping
    @Operation(summary = "Zwraca posortowaną listę filmów dla danej godziny")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Pomyślnie znaleziono filmy") })
    public ResponseEntity<List<VMovieEntity>> getMoviesByTimeRange(@Parameter(description = "Parametry żądania do wyszukania filmów") @ModelAttribute MovieRequest movieRequest,
            @PageableDefault(size = 25) Pageable pageable) {
        Page<VMovieEntity> movies = movieService.getMoviesByTimeRange(movieRequest.getDay(), movieRequest.getTime(), pageable);
        return ResponseEntity.ok(movies.getContent());
    }

}
