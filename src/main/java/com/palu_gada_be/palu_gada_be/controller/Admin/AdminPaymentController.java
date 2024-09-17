package com.palu_gada_be.palu_gada_be.controller.Admin;

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
@RequestMapping(ConstantEndpoint.ADMIN_PAYMENT_API)
@RequiredArgsConstructor
public class AdminPaymentController {

    private final PaymentService paymentService;

    @GetMapping
    public ResponseEntity<?> getAllPayments(
            @PageableDefault Pageable pageable
    ) {
        return Response.renderJSON(
                new PageResponse<>(paymentService.getAll(pageable)),
                "Success Get All Payments",
                HttpStatus.OK
        );
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getPaymentsByUserId(
            @PathVariable Long id,
            @PageableDefault Pageable pageable
    ) {
        return Response.renderJSON(
                new PageResponse<>(paymentService.getByUserId(id, pageable)),
                "Success Get Payments by User ID",
                HttpStatus.OK
        );
    }

    @PutMapping("/{id}/transaction")
    public ResponseEntity<?> updateTransactionStatus(
            @PathVariable Long id,
            @RequestParam(value = "status") String status
    ) throws Exception {
        return Response.renderJSON(
                paymentService.updateTransaction(id, status),
                "Success Update Transaction Status",
                HttpStatus.OK
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
