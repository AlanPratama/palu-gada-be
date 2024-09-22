package com.palu_gada_be.palu_gada_be.service;

import com.palu_gada_be.palu_gada_be.dto.request.UserReportRequest;
import com.palu_gada_be.palu_gada_be.dto.response.UserReportResponse;
import com.palu_gada_be.palu_gada_be.model.UserReport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserReportService {
    UserReportResponse create(UserReportRequest request);
    Page<UserReportResponse> getAll(String messageLikeFilter, List<Long> userReportedIds, List<Long> userReportIds, String sortField, String sortDirection, Pageable pageable);
    Page<UserReportResponse> getByUserId(Pageable pageable);
    UserReport findById(Long id);
    UserReportResponse getById(Long id);
}
