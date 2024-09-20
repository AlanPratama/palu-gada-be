package com.palu_gada_be.palu_gada_be.controller.Admin;

import com.palu_gada_be.palu_gada_be.constant.ConstantEndpoint;
import com.palu_gada_be.palu_gada_be.dto.response.PaymentResponse;
import com.palu_gada_be.palu_gada_be.service.PaymentService;
import com.palu_gada_be.palu_gada_be.util.PageResponse;
import com.palu_gada_be.palu_gada_be.util.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ConstantEndpoint.ADMIN_PAYMENT_API)
@RequiredArgsConstructor
@Tag(name = "Admin Payment", description = "APIs for managing payments by admin")
public class AdminPaymentController {

    private final PaymentService paymentService;

    @Operation(summary = "Get all payments", description = "Retrieve all payments with optional filters for bank, status, and sorting.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved all payments", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PageResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping
    public ResponseEntity<?> getAllPayments(
            @Parameter(description = "Filter by bank") @RequestParam(required = false) String bank,
            @Parameter(description = "Filter by status") @RequestParam(required = false) String status,
            @Parameter(description = "Field to sort by") @RequestParam(required = false) String sortField,
            @Parameter(description = "Sort direction (asc/desc)") @RequestParam(required = false) String sortDirection,
            @PageableDefault Pageable pageable
    ) {
        return Response.renderJSON(
                new PageResponse<>(paymentService.getAll(bank, status, sortField, sortDirection, pageable)),
                "Success Get All Payments",
                HttpStatus.OK
        );
    }

    @Operation(summary = "Get payments by user ID", description = "Retrieve payments made by a specific user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved payments by user ID", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PageResponse.class))),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping("/user/{id}")
    public ResponseEntity<?> getPaymentsByUserId(
            @Parameter(description = "ID of the user") @PathVariable Long id,
            @PageableDefault Pageable pageable
    ) {
        return Response.renderJSON(
                new PageResponse<>(paymentService.getByUserId(id, pageable)),
                "Success Get Payments by User ID",
                HttpStatus.OK
        );
    }

    @Operation(summary = "Update transaction status", description = "Update the status of a specific transaction.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated transaction status", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PaymentResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid transaction status", content = @Content),
            @ApiResponse(responseCode = "404", description = "Transaction not found", content = @Content)
    })
    @PutMapping("/{id}/transaction")
    public ResponseEntity<?> updateTransactionStatus(
            @Parameter(description = "ID of the transaction") @PathVariable Long id,
            @Parameter(description = "New status of the transaction") @RequestParam(value = "status") String status
    ) throws Exception {
        return Response.renderJSON(
                paymentService.updateTransaction(id, status),
                "Success Update Transaction Status",
                HttpStatus.OK
        );
    }

    @Operation(summary = "Fetch transaction", description = "Fetch the details of a specific transaction.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully fetched transaction details", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PaymentResponse.class))),
            @ApiResponse(responseCode = "404", description = "Transaction not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping("/{id}/transaction")
    public ResponseEntity<?> fetchTransaction(
            @Parameter(description = "ID of the transaction") @PathVariable Long id
    ) throws Exception {
        return Response.renderJSON(
                paymentService.fetchTransaction(id),
                "Success Fetch Transaction",
                HttpStatus.OK
        );
    }
}
