package com.palu_gada_be.palu_gada_be.controller.Member;

import com.palu_gada_be.palu_gada_be.constant.ConstantEndpoint;
import com.palu_gada_be.palu_gada_be.dto.request.UserReportRequest;
import com.palu_gada_be.palu_gada_be.service.UserReportService;
import com.palu_gada_be.palu_gada_be.util.PageResponse;
import com.palu_gada_be.palu_gada_be.util.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ConstantEndpoint.MEMBER_USER_REPORT_API)
@RequiredArgsConstructor
public class MemberUserReportController {

    private final UserReportService userReportService;

    @PostMapping
    public ResponseEntity<?> createUserReport(
            @RequestBody UserReportRequest request
    ) {
        return Response.renderJSON(
                userReportService.create(request),
                "Success Create User Report",
                HttpStatus.CREATED
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserReportById(
            @PathVariable Long id
    ) {
        return Response.renderJSON(
                userReportService.getById(id),
                "Success Get User Report",
                HttpStatus.OK
        );
    }

    @GetMapping("/me")
    public ResponseEntity<?> getReportsByUserId(
            @PageableDefault Pageable pageable
    ) {
        return Response.renderJSON(
                new PageResponse<>(userReportService.getByUserId(pageable)),
                "Success Get Reports by User",
                HttpStatus.OK
        );
    }
}
