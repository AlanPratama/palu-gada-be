package com.palu_gada_be.palu_gada_be.service;

import com.palu_gada_be.palu_gada_be.dto.request.ReviewRequest;
import com.palu_gada_be.palu_gada_be.dto.response.ReviewResponse;
import com.palu_gada_be.palu_gada_be.model.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewService {
    ReviewResponse create(ReviewRequest request);
    Page<ReviewResponse> getAll(Pageable pageable);
    Page<ReviewResponse> getByPostId(Long id, Pageable pageable);
    Page<ReviewResponse> getByUserId(Long id, Pageable pageable);
    Page<ReviewResponse> getAllUserAuthenticated(Pageable pageable);
    ReviewResponse getById(Long id);
    Review findById(Long id);
    ReviewResponse updateById(Long id, ReviewRequest request);
    void delete(Long id);
}
