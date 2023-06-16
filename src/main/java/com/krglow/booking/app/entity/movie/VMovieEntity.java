package com.krglow.booking.app.entity.movie;

import java.time.LocalDateTime;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;


@Getter
@Entity
@Immutable
@Table(name = "V_MOVIES")
public class VMovieEntity {

    @Id
    private Long id;

    private String movieTitle;

    private LocalDateTime screeningTime;

}
