package com.krglow.booking.app.service.movie;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.krglow.booking.app.entity.movie.VMovieEntity;
import com.krglow.booking.app.repository.movie.VMovieRepository;

import lombok.AllArgsConstructor;


@Service
@AllArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final VMovieRepository vMovieRepository;

    @Override
    public Page<VMovieEntity> getMoviesByTimeRange(LocalDate day, LocalTime time, Pageable pageable) {
        LocalDateTime startTime = LocalDateTime.of(day, time);
        return vMovieRepository.findByScreeningTimeGreaterThanEqual(startTime, pageable);

    }

}
