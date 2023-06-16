package com.krglow.booking.app.entity;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@MappedSuperclass
public class DeletableEntity {

    private boolean deleted;

}
