package com.palu_gada_be.palu_gada_be.service;

import com.palu_gada_be.palu_gada_be.dto.request.PaymentRequest;
import com.palu_gada_be.palu_gada_be.dto.response.PaymentResponse;
import com.palu_gada_be.palu_gada_be.model.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PaymentService {
    Page<PaymentResponse> getAll(Pageable pageable);
    Page<PaymentResponse> getByUserId(Long id, Pageable pageable);
    Page<PaymentResponse> getByUserAuthenticated(Pageable pageable);
    Payment findById(Long id);
    List<Payment> getPendingPayments();
    PaymentResponse getById(Long id);
    PaymentResponse create(PaymentRequest request) throws Exception;
    PaymentResponse fetchTransaction(Long id) throws Exception;
    PaymentResponse updateTransaction(Long id, String status) throws Exception;
}
