package com.krglow.booking.app.repository.movie;

import org.springframework.data.jpa.repository.JpaRepository;

import com.krglow.booking.app.entity.movie.MovieEntity;


public interface MovieRepository extends JpaRepository<MovieEntity, Long> {

}
