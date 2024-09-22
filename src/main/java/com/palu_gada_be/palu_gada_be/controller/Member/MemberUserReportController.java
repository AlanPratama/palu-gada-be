package com.palu_gada_be.palu_gada_be.controller.Member;

import com.palu_gada_be.palu_gada_be.constant.ConstantEndpoint;
import com.palu_gada_be.palu_gada_be.dto.request.UserReportRequest;
import com.palu_gada_be.palu_gada_be.dto.response.UserReportResponse;
import com.palu_gada_be.palu_gada_be.service.UserReportService;
import com.palu_gada_be.palu_gada_be.util.PageResponse;
import com.palu_gada_be.palu_gada_be.util.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
@RequestMapping(ConstantEndpoint.MEMBER_USER_REPORT_API)
@RequiredArgsConstructor
@Tag(name = "User Reports", description = "User report management APIs")
public class MemberUserReportController {

    private final UserReportService userReportService;

    @Operation(summary = "Create a user report", description = "Create a new report for a user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Report created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserReportResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content)
    })
    @PostMapping
    public ResponseEntity<?> createUserReport(
            @Valid @RequestBody UserReportRequest request
    ) {
        return Response.renderJSON(
                userReportService.create(request),
                "User report created successfully",
                HttpStatus.CREATED
        );
    }

    @Operation(summary = "Get a user report by ID", description = "Retrieve a report by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Report retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserReportResponse.class))),
            @ApiResponse(responseCode = "404", description = "Report not found", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserReportById(
            @PathVariable @Parameter(description = "ID of the user report") Long id
    ) {
        return Response.renderJSON(
                userReportService.getById(id),
                "User report retrieved successfully",
                HttpStatus.OK
        );
    }

    @Operation(summary = "Get reports by user ID", description = "Retrieve all reports for the current user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reports retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PageResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content)
    })
    @GetMapping("/me")
    public ResponseEntity<?> getReportsByUserId(
            @PageableDefault @Parameter(description = "Pagination parameters") Pageable pageable
    ) {
        return Response.renderJSON(
                new PageResponse<>(userReportService.getByUserId(pageable)),
                "Reports retrieved successfully",
                HttpStatus.OK
        );
    }
}
