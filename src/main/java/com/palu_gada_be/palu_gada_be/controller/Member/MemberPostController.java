package com.palu_gada_be.palu_gada_be.controller.Member;

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
@RequestMapping(ConstantEndpoint.MEMBER_POST_API)
@RequiredArgsConstructor
public class MemberPostController {

    private final PostService postService;

    @GetMapping
    public ResponseEntity<?> getAll(
        @PageableDefault Pageable pageable
    ) {
        return Response.renderJSON(
            new PageResponse<>(postService.getAll(pageable)),
            "Success Get Post",
            HttpStatus.OK
        );
    }

    @GetMapping("/me")
    public ResponseEntity<?> getUserAllPost(
        @PageableDefault Pageable pageable
    ) {
        return Response.renderJSON(
            new PageResponse<>(postService.getAllByUserId(pageable)),
            "Success Get Posts",
            HttpStatus.OK
        );
    }

    @PostMapping
    public ResponseEntity<?> create(
        @ModelAttribute PostRequest request,
        @RequestParam(value = "file", required = false) MultipartFile file
    ) {
        return Response.renderJSON(
            postService.create(request, file),
            "Success Create Post",
            HttpStatus.CREATED
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable Long id,
            @ModelAttribute PostRequest request,
            @RequestParam(value = "file", required = false) MultipartFile file
    ) {
        return Response.renderJSON(
                postService.updateById(id, request, file),
                "Success Update Post",
                HttpStatus.OK
        );
    }

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
