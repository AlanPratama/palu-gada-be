package com.palu_gada_be.palu_gada_be.controller.Admin;

import com.palu_gada_be.palu_gada_be.constant.ConstantEndpoint;
import com.palu_gada_be.palu_gada_be.service.ReviewService;
import com.palu_gada_be.palu_gada_be.util.PageResponse;
import com.palu_gada_be.palu_gada_be.util.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ConstantEndpoint.ADMIN_REVIEW_API)
@RequiredArgsConstructor
@Tag(name = "Admin Review", description = "APIs for managing reviews by admin")
public class AdminReviewController {

    private final ReviewService reviewService;

    @Operation(summary = "Get all reviews", description = "Retrieve all reviews with optional filters and pagination.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved all reviews", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PageResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping
    public ResponseEntity<?> getAll(
            @Parameter(description = "Filter by comment content") @RequestParam(required = false) String comment,
            @Parameter(description = "Filter by ratings") @RequestParam(required = false) List<Long> ratings,
            @Parameter(description = "Field to sort by") @RequestParam(required = false) String sortField,
            @Parameter(description = "Sort direction, either asc or desc") @RequestParam(required = false) String sortDirection,
            @PageableDefault Pageable pageable
    ) {
        return Response.renderJSON(
                new PageResponse<>(reviewService.getAll(comment, ratings, sortField, sortDirection, pageable)),
                "Success Get All Reviews",
                HttpStatus.OK
        );
    }

    @Operation(summary = "Get review by ID", description = "Retrieve a specific review by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved review by ID", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PageResponse.class))),
            @ApiResponse(responseCode = "404", description = "Review not found", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(
            @Parameter(description = "ID of the review") @PathVariable Long id
    ) {
        return Response.renderJSON(
                reviewService.getById(id),
                "Success Get Review",
                HttpStatus.OK
        );
    }

    @Operation(summary = "Get reviews by post ID", description = "Retrieve reviews associated with a specific post.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved reviews by post ID", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PageResponse.class))),
            @ApiResponse(responseCode = "404", description = "Reviews not found", content = @Content)
    })
    @GetMapping("/post/{id}")
    public ResponseEntity<?> getByPostId(
            @Parameter(description = "ID of the post") @PathVariable Long id,
            @PageableDefault Pageable pageable
    ) {
        return Response.renderJSON(
                new PageResponse<>(reviewService.getByPostId(id, pageable)),
                "Success Get By Post ID",
                HttpStatus.OK
        );
    }

    @Operation(summary = "Get reviews by user ID", description = "Retrieve reviews submitted by a specific user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved reviews by user ID", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PageResponse.class))),
            @ApiResponse(responseCode = "404", description = "Reviews not found", content = @Content)
    })
    @GetMapping("/user/{id}")
    public ResponseEntity<?> getByUserId(
            @Parameter(description = "ID of the user") @PathVariable Long id,
            @PageableDefault Pageable pageable
    ) {
        return Response.renderJSON(
                new PageResponse<>(reviewService.getByUserId(id, pageable)),
                "Success Get By User ID",
                HttpStatus.OK
        );
    }

    @Operation(summary = "Delete review by ID", description = "Delete a specific review by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted review", content = @Content),
            @ApiResponse(responseCode = "404", description = "Review not found", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
            @Parameter(description = "ID of the review") @PathVariable Long id
    ) {
        reviewService.delete(id);
        return Response.renderJSON(
                id,
                "Success Delete Review",
                HttpStatus.OK
        );
    }
}
