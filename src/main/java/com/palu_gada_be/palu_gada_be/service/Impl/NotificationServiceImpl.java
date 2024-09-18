package com.palu_gada_be.palu_gada_be.service.Impl;

import com.palu_gada_be.palu_gada_be.dto.request.NotificationRequest;
import com.palu_gada_be.palu_gada_be.dto.response.NotificationResponse;
import com.palu_gada_be.palu_gada_be.model.Notification;
import com.palu_gada_be.palu_gada_be.repository.NotificationRepository;
import com.palu_gada_be.palu_gada_be.security.JwtService;
import com.palu_gada_be.palu_gada_be.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final JwtService jwtService;

    @Override
    public NotificationResponse create(NotificationRequest request) {
        
        return null;
    }

    @Override
    public Page<NotificationResponse> getAll(Pageable pageable) {
        return null;
    }

    @Override
    public Page<NotificationResponse> getByUserId(Long id, Pageable pageable) {
        return null;
    }

    @Override
    public Page<NotificationResponse> getByUserAuthenticated(Pageable pageable) {
        return null;
    }

    @Override
    public Notification findById(Long id) {
        return null;
    }

    @Override
    public NotificationResponse getById(Long id) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
