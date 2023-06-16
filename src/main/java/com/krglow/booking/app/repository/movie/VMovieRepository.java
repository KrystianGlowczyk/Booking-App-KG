package com.krglow.booking.app.repository.movie;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.krglow.booking.app.entity.movie.VMovieEntity;


public interface VMovieRepository extends JpaRepository<VMovieEntity, Long> {

    Page<VMovieEntity> findByScreeningTimeGreaterThanEqual(LocalDateTime startTime, Pageable pageable);

}
