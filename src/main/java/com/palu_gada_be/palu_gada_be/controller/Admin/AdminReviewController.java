package com.palu_gada_be.palu_gada_be.controller.Admin;

import com.palu_gada_be.palu_gada_be.constant.ConstantEndpoint;
import com.palu_gada_be.palu_gada_be.service.ReviewService;
import com.palu_gada_be.palu_gada_be.util.PageResponse;
import com.palu_gada_be.palu_gada_be.util.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ConstantEndpoint.ADMIN_REVIEW_API)
@RequiredArgsConstructor
public class AdminReviewController {

    private final ReviewService reviewService;

    @GetMapping
    public ResponseEntity<?> getAll(
        @PageableDefault Pageable pageable
    ) {
        return Response.renderJSON(
            new PageResponse<>(reviewService.getAll(pageable)),
            "Success Get All Review",
            HttpStatus.OK
        );
    }

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

    @GetMapping("/post/{id}")
    public ResponseEntity<?> getByPostId(
        @PathVariable Long id,
        @PageableDefault Pageable pageable
    ) {
        return Response.renderJSON(
            new PageResponse<>(reviewService.getByPostId(id, pageable)),
            "Success Get By Post Id",
            HttpStatus.OK
        );
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getByUserId(
            @PathVariable Long id,
            @PageableDefault Pageable pageable
    ) {
        return Response.renderJSON(
                new PageResponse<>(reviewService.getByUserId(id, pageable)),
                "Success Get By User Id",
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
        @PathVariable Long id
    ) {
        reviewService.delete(id);
        return Response.renderJSON(
            id,
            "Success Delete Review"
        );
    }
}
