package com.palu_gada_be.palu_gada_be.controller.Admin;

import com.palu_gada_be.palu_gada_be.constant.ConstantEndpoint;
import com.palu_gada_be.palu_gada_be.dto.response.PayoutResponse;
import com.palu_gada_be.palu_gada_be.service.PayoutService;
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
@RequestMapping(ConstantEndpoint.ADMIN_PAYOUT_API)
@RequiredArgsConstructor
@Tag(name = "Admin Payout", description = "APIs for managing payouts by admin")
public class AdminPayoutController {

    private final PayoutService payoutService;

    @Operation(summary = "Get all payouts", description = "Retrieve all payout requests with pagination.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved all payouts", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PageResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping
    public ResponseEntity<?> getAll(
            @Parameter(description = "Pagination and sorting parameters") @PageableDefault Pageable pageable
    ) {
        return Response.renderJSON(
                new PageResponse<>(payoutService.getAll(pageable)),
                "Success Get All Payouts",
                HttpStatus.OK
        );
    }

    @Operation(summary = "Get payouts by user ID", description = "Retrieve all payouts for a specific user by their ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved payouts by user ID", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PageResponse.class))),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping("/user/{id}")
    public ResponseEntity<?> getAllByUserId(
            @Parameter(description = "ID of the user") @PathVariable Long id,
            @PageableDefault Pageable pageable
    ) {
        return Response.renderJSON(
                new PageResponse<>(payoutService.getAllByUserId(id, pageable)),
                "Success Get Payouts by User ID",
                HttpStatus.OK
        );
    }

    @Operation(summary = "Get payout by ID", description = "Retrieve a specific payout by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved payout by ID", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PayoutResponse.class))),
            @ApiResponse(responseCode = "404", description = "Payout not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(
            @Parameter(description = "ID of the payout") @PathVariable Long id
    ) {
        return Response.renderJSON(
                payoutService.getById(id),
                "Success Get Payout by ID",
                HttpStatus.OK
        );
    }

    @Operation(summary = "Update payout status", description = "Update the status of a payout.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated payout status", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PayoutResponse.class))),
            @ApiResponse(responseCode = "404", description = "Payout not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid status", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @Parameter(description = "ID of the payout") @PathVariable Long id,
            @Parameter(description = "New status for the payout") @RequestParam String status
    ) {
        return Response.renderJSON(
                payoutService.update(id, status),
                "Success Update Payout Status",
                HttpStatus.OK
        );
    }
}
