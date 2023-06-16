package com.krglow.booking.app.entity.reservation;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.krglow.booking.app.entity.DeletableEntity;
import com.krglow.booking.app.entity.screening.ScreeningEntity;
import com.krglow.booking.app.entity.seat.SeatEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "RESERVATIONS")
public class ReservationEntity extends DeletableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 3)
    @Pattern(regexp = "[A-Z][a-zA-Z]{2,}")
    private String name;

    @NotNull
    @Size(min = 3)
    @Pattern(regexp = "[A-Z][a-zA-Z]+(-[A-Z][a-zA-Z]+)?")
    private String surname;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal totalAmount;

    @Future
    private LocalDateTime expirationTime;

    private boolean confirmed;

    @ManyToOne
    @JoinColumn(name = "screening_id")
    private ScreeningEntity screening;

    @OneToMany(mappedBy = "reservation", cascade = CascadeType.PERSIST)
    private List<SeatEntity> seats;

    private String confirmationToken;

    @Email
    @NotNull
    private String email;

}
