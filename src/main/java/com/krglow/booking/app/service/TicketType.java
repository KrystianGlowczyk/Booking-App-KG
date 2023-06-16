package com.krglow.booking.app.service;

import static lombok.AccessLevel.PRIVATE;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor(access = PRIVATE)
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum TicketType {

    ADULT("Adult", BigDecimal.valueOf(20.00), BigDecimal.valueOf(24.00)), STUDENT("Student", BigDecimal.valueOf(18.00), BigDecimal.valueOf(22.00)),
    CHILD("Child", BigDecimal.valueOf(12.50), BigDecimal.valueOf(16.50));

    private final String name;
    private final BigDecimal price;
    private final BigDecimal weekendPrice;

    public static Optional<TicketType> findByName(String name) {
        return Arrays.stream(TicketType.values()).filter(e -> e.name.equalsIgnoreCase(name)).findFirst();
    }

}
