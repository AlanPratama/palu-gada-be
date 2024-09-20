package com.palu_gada_be.palu_gada_be.controller.Member;

import com.palu_gada_be.palu_gada_be.constant.ConstantEndpoint;
import com.palu_gada_be.palu_gada_be.dto.request.ReviewRequest;
import com.palu_gada_be.palu_gada_be.service.ReviewService;
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
@RequestMapping(ConstantEndpoint.MEMBER_REVIEW_API)
@RequiredArgsConstructor
@Tag(name = "Reviews", description = "APIs for managing reviews by authenticated users")
public class MemberReviewController {

    private final ReviewService reviewService;

    @Operation(summary = "Create a new review", description = "Create a new review for a specific post.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully created review", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ReviewRequest.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request data", content = @Content)
    })
    @PostMapping
    public ResponseEntity<?> create(
            @Valid @RequestBody ReviewRequest request
    ) {
        return Response.renderJSON(
                reviewService.create(request),
                "Success Create Review",
                HttpStatus.OK
        );
    }

    @Operation(summary = "Get review by ID", description = "Retrieve a specific review by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved review", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ReviewRequest.class))),
            @ApiResponse(responseCode = "404", description = "Review not found", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(
            @PathVariable Long id
    ) {
        return Response.renderJSON(
                reviewService.getById(id),
                "Success Get Review",
                HttpStatus.OK
        );
    }

    @Operation(summary = "Get reviews by post ID", description = "Retrieve all reviews for a specific post.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved reviews", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PageResponse.class)))
    })
    @GetMapping("/post/{id}")
    public ResponseEntity<?> getByPostId(
            @PathVariable Long id,
            @PageableDefault Pageable pageable
    ) {
        return Response.renderJSON(
                new PageResponse<>(reviewService.getByPostId(id, pageable)),
                "Success Get Reviews By Post Id",
                HttpStatus.OK
        );
    }

    @Operation(summary = "Get reviews by authenticated user", description = "Retrieve all reviews created by the authenticated user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved reviews", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PageResponse.class)))
    })
    @GetMapping("/me")
    public ResponseEntity<?> getByUserAuthenticated(
            @PageableDefault Pageable pageable
    ) {
        return Response.renderJSON(
                new PageResponse<>(reviewService.getAllUserAuthenticated(pageable)),
                "Success Get Reviews By User Authenticated",
                HttpStatus.OK
        );
    }

    @Operation(summary = "Update review", description = "Update an existing review by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated review", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ReviewRequest.class))),
            @ApiResponse(responseCode = "404", description = "Review not found", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable Long id,
            @Valid @RequestBody ReviewRequest request
    ) {
        return Response.renderJSON(
                reviewService.updateById(id, request),
                "Success Update Review",
                HttpStatus.OK
        );
    }

    @Operation(summary = "Delete review", description = "Delete a specific review by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted review", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(
            @PathVariable Long id
    ) {
        reviewService.delete(id);
        return Response.renderJSON(
                id,
                "Success Delete Review",
                HttpStatus.OK
        );
    }
}
