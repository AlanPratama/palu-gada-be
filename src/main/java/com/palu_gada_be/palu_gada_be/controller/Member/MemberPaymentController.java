package com.palu_gada_be.palu_gada_be.controller.Member;

import com.palu_gada_be.palu_gada_be.constant.ConstantEndpoint;
import com.palu_gada_be.palu_gada_be.dto.request.PaymentRequest;
import com.palu_gada_be.palu_gada_be.service.PaymentService;
import com.palu_gada_be.palu_gada_be.util.PageResponse;
import com.palu_gada_be.palu_gada_be.util.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ConstantEndpoint.MEMBER_PAYMENT_API)
@RequiredArgsConstructor
public class MemberPaymentController {

    private final PaymentService paymentService;

    @GetMapping
    public ResponseEntity<?> getAuthenticatedUserPayments(
            @PageableDefault Pageable pageable
    ) {
        return Response.renderJSON(
                new PageResponse<>(paymentService.getByUserAuthenticated(pageable)),
                "Success Get Payments for Authenticated User",
                HttpStatus.OK
        );
    }

    @PostMapping
    public ResponseEntity<?> createPayment(
            @RequestBody PaymentRequest request
    ) throws Exception {
        return Response.renderJSON(
                paymentService.create(request),
                "Success Create Payment",
                HttpStatus.CREATED
        );
    }

    @GetMapping("/{id}/transaction")
    public ResponseEntity<?> fetchTransaction(
            @PathVariable Long id
    ) throws Exception {
        return Response.renderJSON(
                paymentService.fetchTransaction(id),
                "Success Fetch Transaction",
                HttpStatus.OK
        );
    }
}
