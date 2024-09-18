package com.palu_gada_be.palu_gada_be.service;

import com.palu_gada_be.palu_gada_be.dto.request.BidRequest;
import com.palu_gada_be.palu_gada_be.dto.response.BidResponse;
import com.palu_gada_be.palu_gada_be.model.Bid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BidService {
    BidResponse create(BidRequest request);
    Page<BidResponse> getAll(String message, String status, String sortField, String sortDirection, Pageable pageable);
    Page<BidResponse> getAllByUserId(Pageable pageable);
    Page<BidResponse> getAllByPostId(Long id, Pageable pageable);
    BidResponse updateStatusById(Long id, String status);
    Bid findById(Long id);
    BidResponse getById(Long id);
    void deleteById(Long id);
}
