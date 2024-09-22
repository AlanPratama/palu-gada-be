package com.palu_gada_be.palu_gada_be.service;

import com.palu_gada_be.palu_gada_be.dto.request.PostReportRequest;
import com.palu_gada_be.palu_gada_be.dto.response.PostReportResponse;
import com.palu_gada_be.palu_gada_be.model.PostReport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostReportService {
    PostReportResponse create(PostReportRequest request);
    Page<PostReportResponse> getAll(String messageLikeFilter, String sortField, String sortDirection, Pageable pageable);
    Page<PostReportResponse> getByUserId(Long id, Pageable pageable);
    Page<PostReportResponse> getByPostId(Long id, Pageable pageable);
    Page<PostReportResponse> getByUserAuthenticated(Pageable pageable);
    PostReport findById(Long id);
    PostReportResponse getById(Long id);
    PostReportResponse update(Long id, PostReportRequest request);
}
