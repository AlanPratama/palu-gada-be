package com.palu_gada_be.palu_gada_be.controller.Admin;

import com.palu_gada_be.palu_gada_be.constant.ConstantEndpoint;
import com.palu_gada_be.palu_gada_be.constant.PostStatus;
import com.palu_gada_be.palu_gada_be.dto.response.PostResponse;
import com.palu_gada_be.palu_gada_be.service.PostService;
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
@RequestMapping(ConstantEndpoint.ADMIN_POST_API)
@RequiredArgsConstructor
@Tag(name = "Admin Post", description = "APIs for managing posts by admin")
public class AdminPostController {

    private final PostService postService;

    @Operation(summary = "Get all posts", description = "Retrieve all posts with pagination, filter by title, and district IDs.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved all posts", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PageResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping
    public ResponseEntity<?> getAll(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) List<Long> districtIds,
            @RequestParam(required = false) PostStatus status,
            @RequestParam(required = false) List<Long> categoryIds,
            @RequestParam(required = false) String sortField,
            @RequestParam(required = false) String sortDirection,
            @PageableDefault Pageable pageable
    ) {
        return Response.renderJSON(
                new PageResponse<>(postService.getAll(title, districtIds, status, categoryIds, sortField, sortDirection, pageable)),
                "Success Get All Posts",
                HttpStatus.OK
        );
    }

    @Operation(summary = "Get post by ID", description = "Retrieve a specific post by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved post by ID", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PostResponse.class))),
            @ApiResponse(responseCode = "404", description = "Post not found", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(
            @Parameter(description = "ID of the post") @PathVariable Long id
    ) {
        return Response.renderJSON(
                postService.getById(id),
                "Success Get Post",
                HttpStatus.OK
        );
    }

    @Operation(summary = "Update post status", description = "Update the status of a post.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated post status", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PostResponse.class))),
            @ApiResponse(responseCode = "404", description = "Post not found", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> updateStatus(
            @Parameter(description = "ID of the post") @PathVariable Long id,
            @Parameter(description = "New status for the post") @RequestParam(value = "status") String status
    ) {
        return Response.renderJSON(
                postService.updateStatusPost(id, status),
                "Success Update Post Status",
                HttpStatus.OK
        );
    }

    @Operation(summary = "Delete post by ID", description = "Delete a specific post by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted post", content = @Content),
            @ApiResponse(responseCode = "404", description = "Post not found", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(
            @Parameter(description = "ID of the post") @PathVariable Long id
    ) {
        postService.deleteById(id);
        return Response.renderJSON(
                id,
                "Success Delete Post",
                HttpStatus.OK
        );
    }
}
