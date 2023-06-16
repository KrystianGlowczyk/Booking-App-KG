package com.krglow.booking.app.repository.screening;

import org.springframework.data.jpa.repository.JpaRepository;

import com.krglow.booking.app.entity.screening.ScreeningEntity;


public interface ScreeningRepository extends JpaRepository<ScreeningEntity, Long> {

}
