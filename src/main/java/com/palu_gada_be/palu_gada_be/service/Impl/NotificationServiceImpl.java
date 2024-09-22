package com.palu_gada_be.palu_gada_be.service.Impl;

import com.palu_gada_be.palu_gada_be.dto.request.NotificationRequest;
import com.palu_gada_be.palu_gada_be.dto.response.NotificationResponse;
import com.palu_gada_be.palu_gada_be.mapper.NotificationMapper;
import com.palu_gada_be.palu_gada_be.model.Notification;
import com.palu_gada_be.palu_gada_be.model.User;
import com.palu_gada_be.palu_gada_be.repository.NotificationRepository;
import com.palu_gada_be.palu_gada_be.security.JwtService;
import com.palu_gada_be.palu_gada_be.service.NotificationService;
import com.palu_gada_be.palu_gada_be.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserService userService;
    private final JwtService jwtService;

    @Override
    public NotificationResponse create(NotificationRequest request) {
        User user = userService.findById(request.getUserId());

        Notification notification = Notification.builder()
                .user(user)
                .title(request.getTitle())
                .description(request.getDescription())
                .icon(request.getIcon())
                .isRead(request.getIsRead())
                .build();

        Notification createdNotification = notificationRepository.save(notification);

        return NotificationMapper.toNotificationResponse(createdNotification);
    }

    @Override
    public Page<NotificationResponse> getAll(Pageable pageable) {
        Page<Notification> notifications = notificationRepository.findAll(pageable);

        return notifications.map(NotificationMapper::toNotificationResponse);
    }

    @Override
    public Page<NotificationResponse> getByUserId(Long id, Pageable pageable) {
        Page<Notification> notifications = notificationRepository.findByUserId(id, pageable);

        return notifications.map(NotificationMapper::toNotificationResponse);
    }

    @Override
    public Page<NotificationResponse> getByUserAuthenticated(Pageable pageable) {
        Long id = jwtService.getUserAuthenticated().getId();

        return this.getByUserId(id, pageable);
    }

    @Override
    public Notification findById(Long id) {
        return notificationRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Notification not found")
        );
    }

    @Override
    public NotificationResponse getById(Long id) {
        Notification notification = this.findById(id);

        return NotificationMapper.toNotificationResponse(notification);
    }

    @Override
    public void updateIsReadByUser() {
        Long userId = jwtService.getUserAuthenticated().getId();

        List<Notification> notifications = notificationRepository.findByUserId(userId);

        notifications.forEach(notification -> notification.setIsRead(true));

        notificationRepository.saveAll(notifications);
    }


    @Override
    public void deleteById(Long id) {
        Notification notification = findById(id);

        notificationRepository.delete(notification);
    }
}
