package com.krglow.booking.app.entity.voucher;

import java.math.BigDecimal;

import com.krglow.booking.app.entity.DeletableEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "VOUCHERS")
public class VoucherEntity extends DeletableEntity {

    @Id
    private String code;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    @DecimalMax(value = "100.0")
    @Column(precision = 5, scale = 2)
    private BigDecimal discount;

}
