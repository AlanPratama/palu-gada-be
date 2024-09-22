package com.palu_gada_be.palu_gada_be.controller.Member;

import com.palu_gada_be.palu_gada_be.constant.ConstantEndpoint;
import com.palu_gada_be.palu_gada_be.dto.request.NotificationRequest;
import com.palu_gada_be.palu_gada_be.dto.response.NotificationResponse;
import com.palu_gada_be.palu_gada_be.service.NotificationService;
import com.palu_gada_be.palu_gada_be.util.PageResponse;
import com.palu_gada_be.palu_gada_be.util.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Notifications", description = "APIs for managing notifications")
public class MemberNotificationController {

    private final NotificationService notificationService;

    @Operation(summary = "Get all notifications", description = "Retrieve a paginated list of all notifications.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved notifications", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PageResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters", content = @Content)
    })
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

    @Operation(summary = "Get authenticated user's notifications", description = "Retrieve a paginated list of notifications for the authenticated user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved user notifications", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PageResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
    })
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

    @Operation(summary = "Get notifications by user ID", description = "Retrieve a paginated list of notifications for a specific user by their ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved user notifications", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PageResponse.class))),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
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

    @Operation(summary = "Create a notification", description = "Create a new notification.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Notification created successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = NotificationResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content)
    })
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

    @Operation(summary = "Get notification by ID", description = "Retrieve a notification by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved notification", content = @Content(mediaType = "application/json", schema = @Schema(implementation = NotificationResponse.class))),
            @ApiResponse(responseCode = "404", description = "Notification not found", content = @Content)
    })
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

    @Operation(summary = "Mark all notifications as read", description = "Mark all notifications for the authenticated user as read.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully marked notifications as read"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
    })
    @PutMapping("/me/read")
    public ResponseEntity<?> markAllNotificationsAsRead() {
        notificationService.updateIsReadByUser();
        return Response.renderJSON(
                null,
                "Success Mark All Notifications as Read",
                HttpStatus.OK
        );
    }

    @Operation(summary = "Delete notification by ID", description = "Delete a notification by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted notification"),
            @ApiResponse(responseCode = "404", description = "Notification not found", content = @Content)
    })
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
