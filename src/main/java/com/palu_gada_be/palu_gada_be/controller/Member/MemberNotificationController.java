package com.palu_gada_be.palu_gada_be.controller.Member;

import com.palu_gada_be.palu_gada_be.constant.ConstantEndpoint;
import com.palu_gada_be.palu_gada_be.dto.request.NotificationRequest;
import com.palu_gada_be.palu_gada_be.service.NotificationService;
import com.palu_gada_be.palu_gada_be.util.PageResponse;
import com.palu_gada_be.palu_gada_be.util.Response;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ConstantEndpoint.MEMBER_NOTIFICATION_API)
@RequiredArgsConstructor
public class MemberNotificationController {

    private final NotificationService notificationService;

    @GetMapping
    public ResponseEntity<?> getAll(
            @PageableDefault Pageable pageable
    ) {
        return Response.renderJSON(
                new PageResponse<>(notificationService.getAll(pageable)),
                "Success Get Notifications",
                HttpStatus.OK
        );
    }

    @GetMapping("/me")
    public ResponseEntity<?> getUserAuthenticatedNotifications(
            @PageableDefault Pageable pageable
    ) {
        return Response.renderJSON(
                new PageResponse<>(notificationService.getByUserAuthenticated(pageable)),
                "Success Get User Notifications",
                HttpStatus.OK
        );
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUserNotificationsById(
            @PathVariable Long userId,
            @PageableDefault Pageable pageable
    ) {
        return Response.renderJSON(
                new PageResponse<>(notificationService.getByUserId(userId, pageable)),
                "Success Get User Notifications",
                HttpStatus.OK
        );
    }

    @PostMapping
    public ResponseEntity<?> create(
            @Valid @RequestBody NotificationRequest request
    ) {
        return Response.renderJSON(
                notificationService.create(request),
                "Success Create Notification",
                HttpStatus.CREATED
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getNotificationById(
            @PathVariable Long id
    ) {
        return Response.renderJSON(
                notificationService.getById(id),
                "Success Get Notification",
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteNotificationById(
            @PathVariable Long id
    ) {
        notificationService.deleteById(id);
        return Response.renderJSON(
                null,
                "Success Delete Notification",
                HttpStatus.NO_CONTENT
        );
    }
}
