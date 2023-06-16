package com.krglow.booking.app.repository.voucher;

import org.springframework.data.jpa.repository.JpaRepository;

import com.krglow.booking.app.entity.voucher.VoucherEntity;


public interface VoucherRepository extends JpaRepository<VoucherEntity, String> {

}
