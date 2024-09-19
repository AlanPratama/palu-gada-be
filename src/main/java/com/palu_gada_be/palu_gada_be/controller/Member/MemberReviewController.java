package com.palu_gada_be.palu_gada_be.controller.Member;

import com.palu_gada_be.palu_gada_be.constant.ConstantEndpoint;
import com.palu_gada_be.palu_gada_be.dto.request.ReviewRequest;
import com.palu_gada_be.palu_gada_be.service.ReviewService;
import com.palu_gada_be.palu_gada_be.util.PageResponse;
import com.palu_gada_be.palu_gada_be.util.Response;
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
public class MemberReviewController {

    private final ReviewService reviewService;

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

    @GetMapping("/me")
    public ResponseEntity<?> getByUserAuthenticated(
            @PageableDefault Pageable pageable
    ) {
        return Response.renderJSON(
                new PageResponse<>(reviewService.getAllUserAuthenticated(pageable)),
                "Success Get By User Authenticated",
                HttpStatus.OK
        );
    }

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

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(
        @PathVariable Long id
    ) {
        reviewService.delete(id);
        return Response.renderJSON(
            id,
            "Success Delete Review"
        );
    }
}
