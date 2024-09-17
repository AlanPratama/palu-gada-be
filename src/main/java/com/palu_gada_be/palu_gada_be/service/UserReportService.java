package com.palu_gada_be.palu_gada_be.service;

import com.palu_gada_be.palu_gada_be.dto.request.UserReportRequest;
import com.palu_gada_be.palu_gada_be.dto.response.UserReportResponse;
import com.palu_gada_be.palu_gada_be.model.UserReport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserReportService {
    UserReportResponse create(UserReportRequest request);
    Page<UserReportResponse> getAll(Pageable pageable);
    Page<UserReportResponse> getByUserId(Pageable pageable);
    UserReport findById(Long id);
    UserReportResponse getById(Long id);
}
