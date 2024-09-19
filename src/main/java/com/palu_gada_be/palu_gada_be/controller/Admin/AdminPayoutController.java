package com.palu_gada_be.palu_gada_be.controller.Admin;

import com.palu_gada_be.palu_gada_be.constant.ConstantEndpoint;
import com.palu_gada_be.palu_gada_be.dto.request.PayoutRequest;
import com.palu_gada_be.palu_gada_be.service.PayoutService;
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
@RequestMapping(ConstantEndpoint.ADMIN_PAYOUT_API)
@RequiredArgsConstructor
public class AdminPayoutController {

    private final PayoutService payoutService;

    @GetMapping
    public ResponseEntity<?> getAll(
            @PageableDefault Pageable pageable
    ) {
        return Response.renderJSON(
                new PageResponse<>(payoutService.getAll(pageable)),
                "Success Get All Payouts",
                HttpStatus.OK
        );
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getAllByUserId(
            @PathVariable Long id,
            @PageableDefault Pageable pageable
    ) {
        return Response.renderJSON(
                new PageResponse<>(payoutService.getAllByUserId(id, pageable)),
                "Success Get Payouts by User ID",
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(
            @PathVariable Long id
    ) {
        return Response.renderJSON(
                payoutService.getById(id),
                "Success Get Payout by ID",
                HttpStatus.OK
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable Long id,
            @RequestParam String status
    ) {
        return Response.renderJSON(
                payoutService.update(id, status),
                "Success Update Payout Status",
                HttpStatus.OK
        );
    }
}
