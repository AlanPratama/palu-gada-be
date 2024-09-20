package com.palu_gada_be.palu_gada_be.controller.Member;

import com.palu_gada_be.palu_gada_be.constant.ConstantEndpoint;
import com.palu_gada_be.palu_gada_be.dto.request.PaymentRequest;
import com.palu_gada_be.palu_gada_be.dto.response.PaymentResponse;
import com.palu_gada_be.palu_gada_be.service.PaymentService;
import com.palu_gada_be.palu_gada_be.util.PageResponse;
import com.palu_gada_be.palu_gada_be.util.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ConstantEndpoint.MEMBER_PAYMENT_API)
@RequiredArgsConstructor
@Tag(name = "Payments", description = "APIs for managing user payments")
public class MemberPaymentController {

    private final PaymentService paymentService;

    @Operation(summary = "Get authenticated user's payments", description = "Retrieve a paginated list of payments for the authenticated user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved payments", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PageResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
    })
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

    @Operation(summary = "Create a payment", description = "Create a new payment.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Payment successfully created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PaymentResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content)
    })
    @PostMapping
    public ResponseEntity<?> createPayment(
            @Valid @RequestBody PaymentRequest request
    ) throws Exception {
        return Response.renderJSON(
                paymentService.create(request),
                "Success Create Payment",
                HttpStatus.CREATED
        );
    }

    @Operation(summary = "Fetch a transaction", description = "Fetch transaction details for a specific payment by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully fetched transaction details", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PaymentResponse.class))),
            @ApiResponse(responseCode = "404", description = "Payment not found", content = @Content)
    })
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
