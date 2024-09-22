package com.palu_gada_be.palu_gada_be.controller.Member;

import com.palu_gada_be.palu_gada_be.constant.ConstantEndpoint;
import com.palu_gada_be.palu_gada_be.dto.request.PayoutRequest;
import com.palu_gada_be.palu_gada_be.dto.response.PayoutResponse;
import com.palu_gada_be.palu_gada_be.service.PayoutService;
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
@RequestMapping(ConstantEndpoint.MEMBER_PAYOUT_API)
@RequiredArgsConstructor
@Tag(name = "Payouts", description = "APIs for managing payouts")
public class MemberPayoutController {

    private final PayoutService payoutService;

    @Operation(summary = "Create a new payout", description = "Create a new payout for the authenticated user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Payout successfully created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PayoutResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request data", content = @Content)
    })
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

    @Operation(summary = "Get payout by ID", description = "Retrieve the details of a specific payout by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved payout", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PayoutResponse.class))),
            @ApiResponse(responseCode = "404", description = "Payout not found", content = @Content)
    })
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

    @Operation(summary = "Get authenticated user's payouts", description = "Retrieve a paginated list of payouts for the authenticated user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved user's payouts", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PageResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
    })
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
