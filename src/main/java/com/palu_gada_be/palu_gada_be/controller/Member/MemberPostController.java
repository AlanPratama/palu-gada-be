package com.palu_gada_be.palu_gada_be.controller.Member;

import com.palu_gada_be.palu_gada_be.constant.ConstantEndpoint;
import com.palu_gada_be.palu_gada_be.constant.PostStatus;
import com.palu_gada_be.palu_gada_be.dto.request.PostRequest;
import com.palu_gada_be.palu_gada_be.dto.response.PostResponse;
import com.palu_gada_be.palu_gada_be.service.PostService;
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
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(ConstantEndpoint.MEMBER_POST_API)
@RequiredArgsConstructor
@Tag(name = "Posts", description = "APIs for managing posts")
public class MemberPostController {

    private final PostService postService;

    @Operation(summary = "Get all posts", description = "Retrieve all posts with optional filters and sorting.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved posts", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PageResponse.class)))
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
                "Success Get Post",
                HttpStatus.OK
        );
    }

    @Operation(summary = "Get all posts by the authenticated user", description = "Retrieve all posts created by the authenticated user with optional filters.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved user's posts", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PageResponse.class)))
    })
    @GetMapping("/me")
    public ResponseEntity<?> getUserAllPost(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) PostStatus status,
            @RequestParam(required = false) List<Long> categoryIds,
            @RequestParam(required = false) String sortField,
            @RequestParam(required = false) String sortDirection,
            @PageableDefault Pageable pageable
    ) {
        return Response.renderJSON(
                new PageResponse<>(postService.getAllByUserId(title, status, categoryIds, sortField, sortDirection, pageable)),
                "Success Get Posts",
                HttpStatus.OK
        );
    }

    @Operation(summary = "Create a new post", description = "Create a new post with an optional file upload.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created post", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PostResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request data", content = @Content)
    })
    @PostMapping
    public ResponseEntity<?> create(
            @Valid @ModelAttribute PostRequest request,
            @RequestParam(value = "file", required = false) MultipartFile file
    ) {
        return Response.renderJSON(
                postService.create(request, file),
                "Success Create Post",
                HttpStatus.CREATED
        );
    }

    @Operation(summary = "Update a post", description = "Update an existing post by its ID with an optional file.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated post", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PostResponse.class))),
            @ApiResponse(responseCode = "404", description = "Post not found", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable Long id,
            @Valid @ModelAttribute PostRequest request,
            @RequestParam(value = "file", required = false) MultipartFile file
    ) {
        return Response.renderJSON(
                postService.updateById(id, request, file),
                "Success Update Post",
                HttpStatus.OK
        );
    }

    @Operation(summary = "Delete post", description = "delete the post by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted post", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PostResponse.class))),
            @ApiResponse(responseCode = "404", description = "Post not found", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
            @PathVariable Long id
    ) {
        postService.deleteById(id);
        return Response.renderJSON(
                id,
                "Success Delete Post",
                HttpStatus.OK
        );
    }

    @Operation(summary = "Update post status", description = "Update the status of a post by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated post status", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PostResponse.class))),
            @ApiResponse(responseCode = "404", description = "Post not found", content = @Content)
    })
    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateStatus(
            @PathVariable Long id,
            @RequestParam(value = "status") String status
    ) {
        return Response.renderJSON(
                postService.updateStatusPost(id, status),
                "Success Update Post Status",
                HttpStatus.OK
        );
    }

    @Operation(summary = "Get post by ID", description = "Retrieve a specific post by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved post", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PostResponse.class))),
            @ApiResponse(responseCode = "404", description = "Post not found", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getPostById(
            @PathVariable Long id
    ) {
        return Response.renderJSON(
                postService.getById(id),
                "Success Get Post",
                HttpStatus.OK
        );
    }
}
