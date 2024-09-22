package com.palu_gada_be.palu_gada_be.controller.Member;

import com.palu_gada_be.palu_gada_be.constant.ConstantEndpoint;
import com.palu_gada_be.palu_gada_be.dto.request.PostReportRequest;
import com.palu_gada_be.palu_gada_be.service.PostReportService;
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
@RequestMapping(ConstantEndpoint.MEMBER_POST_REPORT_API)
@RequiredArgsConstructor
@Tag(name = "Post Reports", description = "APIs for managing post reports by authenticated users")
public class MemberPostReportController {

    private final PostReportService postReportService;

    @Operation(summary = "Get post reports by authenticated user", description = "Retrieve all post reports created by the authenticated user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved post reports", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PageResponse.class)))
    })
    @GetMapping
    public ResponseEntity<?> getByUserAuthenticated(
            @PageableDefault Pageable pageable
    ) {
        return Response.renderJSON(
                new PageResponse<>(postReportService.getByUserAuthenticated(pageable)),
                "Success Get Post Reports By Authenticated User",
                HttpStatus.OK
        );
    }

    @Operation(summary = "Create a post report", description = "Create a new report for a specific post.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created post report", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PostReportRequest.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request data", content = @Content)
    })
    @PostMapping
    public ResponseEntity<?> create(
            @Valid @RequestBody PostReportRequest request
    ) {
        return Response.renderJSON(
                postReportService.create(request),
                "Success Create Post Report",
                HttpStatus.CREATED
        );
    }

    @Operation(summary = "Get post report by ID", description = "Retrieve a specific post report by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved post report", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PostReportRequest.class))),
            @ApiResponse(responseCode = "404", description = "Post report not found", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(
            @PathVariable Long id
    ) {
        return Response.renderJSON(
                postReportService.getById(id),
                "Success Get Post Report By Id",
                HttpStatus.OK
        );
    }

    @Operation(summary = "Get post reports by post ID", description = "Retrieve all reports for a specific post.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved post reports", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PageResponse.class)))
    })
    @GetMapping("/post/{id}")
    public ResponseEntity<?> getByPostId(
            @PathVariable Long id,
            @PageableDefault Pageable pageable
    ) {
        return Response.renderJSON(
                new PageResponse<>(postReportService.getByPostId(id, pageable)),
                "Success Get Post Reports By Post Id",
                HttpStatus.OK
        );
    }

    @Operation(summary = "Update a post report", description = "Update an existing post report by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated post report", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PostReportRequest.class))),
            @ApiResponse(responseCode = "404", description = "Post report not found", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable Long id,
            @Valid @RequestBody PostReportRequest request
    ) {
        return Response.renderJSON(
                postReportService.update(id, request),
                "Success Update Post Report",
                HttpStatus.OK
        );
    }
}
