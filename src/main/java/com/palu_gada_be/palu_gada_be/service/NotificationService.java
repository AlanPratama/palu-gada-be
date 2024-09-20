package com.palu_gada_be.palu_gada_be.service;

import com.palu_gada_be.palu_gada_be.dto.request.NotificationRequest;
import com.palu_gada_be.palu_gada_be.dto.response.NotificationResponse;
import com.palu_gada_be.palu_gada_be.model.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NotificationService {
    NotificationResponse create(NotificationRequest request);
    Page<NotificationResponse> getAll(Pageable pageable);
    Page<NotificationResponse> getByUserId(Long id, Pageable pageable);
    Page<NotificationResponse> getByUserAuthenticated(Pageable pageable);
    Notification findById(Long id);
    NotificationResponse getById(Long id);
    void updateIsReadByUser();
    void deleteById(Long id);
}
