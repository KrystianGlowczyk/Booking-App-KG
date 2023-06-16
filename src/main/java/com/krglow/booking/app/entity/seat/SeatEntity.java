package com.krglow.booking.app.entity.seat;

import com.krglow.booking.app.entity.DeletableEntity;
import com.krglow.booking.app.entity.reservation.ReservationEntity;
import com.krglow.booking.app.entity.screening.ScreeningEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "SEATS")
public class SeatEntity extends DeletableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "screening_id")
    private ScreeningEntity screening;

    @NotNull
    @Min(1)
    private Long rowNumber;

    @NotNull
    @Min(1)
    private Long number;

    private boolean reserved;

    @ManyToOne
    @JoinColumn(name = "reservation_id")
    private ReservationEntity reservation;

    public String getSeatIdentifier() {
        return this.rowNumber + "-" + this.number;
    }

}
