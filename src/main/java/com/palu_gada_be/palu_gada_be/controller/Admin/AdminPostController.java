package com.palu_gada_be.palu_gada_be.controller.Admin;

import com.palu_gada_be.palu_gada_be.constant.ConstantEndpoint;
import com.palu_gada_be.palu_gada_be.dto.request.PostRequest;
import com.palu_gada_be.palu_gada_be.service.PostService;
import com.palu_gada_be.palu_gada_be.util.PageResponse;
import com.palu_gada_be.palu_gada_be.util.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(ConstantEndpoint.ADMIN_POST_API)
@RequiredArgsConstructor
public class AdminPostController {

    private final PostService postService;

    @GetMapping
    public ResponseEntity<?> getAll(
        @PageableDefault Pageable pageable
    ) {
        return Response.renderJSON(
            new PageResponse<>(postService.getAll(pageable)),
            "Success Get All Posts",
            HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(
        @PathVariable Long id
    ) {
        return Response.renderJSON(
            postService.getById(id),
            "Success Get Post",
            HttpStatus.OK
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateStatus(
        @PathVariable Long id,
        @RequestParam(value = "status") String request
    ) {
        return Response.renderJSON(
            postService.updateStatusPost(id, request),
            "Success Update Post Status",
            HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(
        @PathVariable Long id
    ) {
        postService.deleteById(id);
        return Response.renderJSON(
            id,
            "Success Delete Post"
        );
    }
}
