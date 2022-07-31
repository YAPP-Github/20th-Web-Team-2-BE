package com.yapp.lonessum.domain.payment.repository;

import com.yapp.lonessum.domain.payment.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {
}
