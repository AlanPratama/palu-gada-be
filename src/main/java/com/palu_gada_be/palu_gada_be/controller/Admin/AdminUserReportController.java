package com.palu_gada_be.palu_gada_be.controller.Admin;

import com.palu_gada_be.palu_gada_be.constant.ConstantEndpoint;
import com.palu_gada_be.palu_gada_be.dto.request.UserReportRequest;
import com.palu_gada_be.palu_gada_be.dto.response.UserReportResponse;
import com.palu_gada_be.palu_gada_be.service.UserReportService;
import com.palu_gada_be.palu_gada_be.util.PageResponse;
import com.palu_gada_be.palu_gada_be.util.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ConstantEndpoint.ADMIN_USER_REPORT_API)
@RequiredArgsConstructor
@Tag(name = "Admin User Reports", description = "APIs for managing user reports by admin")
public class AdminUserReportController {

    private final UserReportService userReportService;

    @Operation(summary = "Get all user reports", description = "Retrieve all user reports with optional filters and pagination.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved all user reports", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PageResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping
    public ResponseEntity<?> getAllUserReports(
            @Parameter(description = "Filter by report message") @RequestParam(required = false) String message,
            @Parameter(description = "Filter by reported user IDs") @RequestParam(required = false) List<Long> userReportedIds,
            @Parameter(description = "Filter by reporting user IDs") @RequestParam(required = false) List<Long> userReportIds,
            @Parameter(description = "Field to sort by") @RequestParam(required = false) String sortField,
            @Parameter(description = "Sort direction, either asc or desc") @RequestParam(required = false) String sortDirection,
            @PageableDefault Pageable pageable
    ) {
        return Response.renderJSON(
                new PageResponse<>(userReportService.getAll(message, userReportedIds, userReportIds, sortField, sortDirection, pageable)),
                "Success Get User Reports",
                HttpStatus.OK
        );
    }

    @Operation(summary = "Get user report by ID", description = "Retrieve a specific user report by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved user report", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserReportResponse.class))),
            @ApiResponse(responseCode = "404", description = "User report not found", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserReportById(
            @Parameter(description = "ID of the user report") @PathVariable Long id
    ) {
        return Response.renderJSON(
                userReportService.getById(id),
                "Success Get User Report",
                HttpStatus.OK
        );
    }

    @Operation(summary = "Get reports by user ID", description = "Retrieve all reports filed by a specific user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved user reports", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PageResponse.class))),
            @ApiResponse(responseCode = "404", description = "No reports found for this user", content = @Content)
    })
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getReportsByUserId(
            @Parameter(description = "ID of the reporting user") @PathVariable Long userId,
            @PageableDefault Pageable pageable
    ) {
        return Response.renderJSON(
                new PageResponse<>(userReportService.getByUserId(pageable)),
                "Success Get Reports by User",
                HttpStatus.OK
        );
    }
}
