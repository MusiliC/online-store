package com.ceetech.paymentservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ceetech.paymentservice.entity.PaymentEntity;

public interface PaymentRepository extends JpaRepository<PaymentEntity, Integer> {

}
