package com.palu_gada_be.palu_gada_be.controller.Admin;

import com.palu_gada_be.palu_gada_be.constant.ConstantEndpoint;
import com.palu_gada_be.palu_gada_be.dto.response.PostReportResponse;
import com.palu_gada_be.palu_gada_be.service.PostReportService;
import com.palu_gada_be.palu_gada_be.util.PageResponse;
import com.palu_gada_be.palu_gada_be.util.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ConstantEndpoint.ADMIN_POST_REPORT_API)
@RequiredArgsConstructor
@Tag(name = "Admin Post Report", description = "APIs for managing post reports by admin")
public class AdminPostReportController {

    private final PostReportService postReportService;

    @Operation(summary = "Get all post reports", description = "Retrieve all post reports with optional filters and pagination.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved all post reports", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PageResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping
    public ResponseEntity<?> getAll(
            @Parameter(description = "Filter by message content") @RequestParam(required = false) String message,
            @Parameter(description = "Field to sort by") @RequestParam(required = false) String sortField,
            @Parameter(description = "Sort direction, either asc or desc") @RequestParam(required = false) String sortDirection,
            @PageableDefault Pageable pageable
    ) {
        return Response.renderJSON(
                new PageResponse<>(postReportService.getAll(message, sortField, sortDirection, pageable)),
                "Success Get All Post Reports",
                HttpStatus.OK
        );
    }

    @Operation(summary = "Get post reports by user ID", description = "Retrieve post reports created by a specific user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved post reports by user ID", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PageResponse.class))),
            @ApiResponse(responseCode = "404", description = "Post reports not found", content = @Content)
    })
    @GetMapping("/user/{id}")
    public ResponseEntity<?> getByUserId(
            @Parameter(description = "ID of the user") @PathVariable Long id,
            @PageableDefault Pageable pageable
    ) {
        return Response.renderJSON(
                new PageResponse<>(postReportService.getByUserId(id, pageable)),
                "Success Get Post Reports By User ID",
                HttpStatus.OK
        );
    }

    @Operation(summary = "Get post reports by post ID", description = "Retrieve post reports related to a specific post.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved post reports by post ID", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PageResponse.class))),
            @ApiResponse(responseCode = "404", description = "Post reports not found", content = @Content)
    })
    @GetMapping("/post/{id}")
    public ResponseEntity<?> getByPostId(
            @Parameter(description = "ID of the post") @PathVariable Long id,
            @PageableDefault Pageable pageable
    ) {
        return Response.renderJSON(
                new PageResponse<>(postReportService.getByPostId(id, pageable)),
                "Success Get Post Reports By Post ID",
                HttpStatus.OK
        );
    }

    @Operation(summary = "Get post report by ID", description = "Retrieve a specific post report by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved post report by ID", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PostReportResponse.class))),
            @ApiResponse(responseCode = "404", description = "Post report not found", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(
            @Parameter(description = "ID of the post report") @PathVariable Long id
    ) {
        return Response.renderJSON(
                postReportService.getById(id),
                "Success Get Post Report By ID",
                HttpStatus.OK
        );
    }
}
