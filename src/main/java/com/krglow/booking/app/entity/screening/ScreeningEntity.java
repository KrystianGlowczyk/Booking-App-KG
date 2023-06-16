package com.krglow.booking.app.entity.screening;

import java.time.LocalDateTime;
import java.util.List;

import com.krglow.booking.app.entity.DeletableEntity;
import com.krglow.booking.app.entity.movie.MovieEntity;
import com.krglow.booking.app.entity.room.ScreeningRoomEntity;
import com.krglow.booking.app.entity.seat.SeatEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Future;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "SCREENINGS")
public class ScreeningEntity extends DeletableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private MovieEntity movie;

    @ManyToOne
    private ScreeningRoomEntity screeningRoom;

    @Future
    private LocalDateTime startTime;

    @OneToMany(mappedBy = "screening", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SeatEntity> seats;

}
