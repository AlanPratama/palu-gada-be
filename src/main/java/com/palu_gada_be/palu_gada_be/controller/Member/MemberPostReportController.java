package com.palu_gada_be.palu_gada_be.controller.Member;

import com.palu_gada_be.palu_gada_be.constant.ConstantEndpoint;
import com.palu_gada_be.palu_gada_be.dto.request.PostReportRequest;
import com.palu_gada_be.palu_gada_be.service.PostReportService;
import com.palu_gada_be.palu_gada_be.util.PageResponse;
import com.palu_gada_be.palu_gada_be.util.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ConstantEndpoint.MEMBER_POST_REPORT_API)
@RequiredArgsConstructor
public class MemberPostReportController {

    private final PostReportService postReportService;

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

    @PostMapping
    public ResponseEntity<?> create(
            @RequestBody PostReportRequest request
    ) {
        return Response.renderJSON(
                postReportService.create(request),
                "Success Create Post Report",
                HttpStatus.CREATED
        );
    }

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

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable Long id,
            @RequestBody PostReportRequest request
    ) {
        return Response.renderJSON(
                postReportService.update(id, request),
                "Success Update Post Report",
                HttpStatus.OK
        );
    }
}
