package com.palu_gada_be.palu_gada_be.controller.Member;

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
@RequestMapping(ConstantEndpoint.MEMBER_PAYOUT_API)
@RequiredArgsConstructor
public class MemberPayoutController {

    private final PayoutService payoutService;

    @PostMapping
    public ResponseEntity<?> create(
            @Valid @RequestBody PayoutRequest request
    ) {
        return Response.renderJSON(
                payoutService.create(request),
                "Success Create Payout",
                HttpStatus.CREATED
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

    @GetMapping("/me")
    public ResponseEntity<?> getAllUserAuthenticated(
            @PageableDefault Pageable pageable
    ) {
        return Response.renderJSON(
                new PageResponse<>(payoutService.getAllUserAuthenticated(pageable)),
                "Success Get Authenticated User's Payouts",
                HttpStatus.OK
        );
    }

}
