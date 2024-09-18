package com.palu_gada_be.palu_gada_be.repository;

import com.palu_gada_be.palu_gada_be.constant.PaymentStatus;
import com.palu_gada_be.palu_gada_be.model.District;
import com.palu_gada_be.palu_gada_be.model.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long>, JpaSpecificationExecutor<Payment> {
    List<Payment> findByUserId(Long id);
    Page<Payment> findByUserId(Long id, Pageable pageable);
    List<Payment> findByPaymentStatus(PaymentStatus status);
}
