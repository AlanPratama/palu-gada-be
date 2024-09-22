package com.palu_gada_be.palu_gada_be.service;

import com.palu_gada_be.palu_gada_be.dto.request.PayoutRequest;
import com.palu_gada_be.palu_gada_be.dto.response.PayoutResponse;
import com.palu_gada_be.palu_gada_be.model.Payout;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PayoutService {
    PayoutResponse create(PayoutRequest request);
    Page<PayoutResponse> getAll(Pageable pageable);
    Page<PayoutResponse> getAllByUserId(Long id, Pageable pageable);
    Page<PayoutResponse> getAllUserAuthenticated(Pageable pageable);
    Payout findById(Long id);
    PayoutResponse getById(Long id);
    PayoutResponse update(Long id, String status);
}
