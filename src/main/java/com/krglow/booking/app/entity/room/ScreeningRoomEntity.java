package com.krglow.booking.app.entity.room;

import com.krglow.booking.app.entity.DeletableEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "SCREENING_ROOMS")
public class ScreeningRoomEntity extends DeletableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 1)
    private String roomName;

    @NotNull
    @Min(1)
    private Long numberOfRows;

    @NotNull
    @Min(1)
    private Long seatsPerRow;

}
