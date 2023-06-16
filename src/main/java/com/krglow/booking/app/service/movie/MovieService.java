package com.krglow.booking.app.service.movie;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.krglow.booking.app.entity.movie.VMovieEntity;


public interface MovieService {

    Page<VMovieEntity> getMoviesByTimeRange(LocalDate day, LocalTime time, Pageable pageable);

}
