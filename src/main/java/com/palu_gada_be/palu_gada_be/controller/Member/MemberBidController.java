package com.palu_gada_be.palu_gada_be.controller.Member;

import com.palu_gada_be.palu_gada_be.constant.ConstantEndpoint;
import com.palu_gada_be.palu_gada_be.dto.request.BidRequest;
import com.palu_gada_be.palu_gada_be.dto.response.BidResponse;
import com.palu_gada_be.palu_gada_be.service.BidService;
import com.palu_gada_be.palu_gada_be.util.PageResponse;
import com.palu_gada_be.palu_gada_be.util.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ConstantEndpoint.MEMBER_BID_API)
@RequiredArgsConstructor
@Tag(name = "Member Bid", description = "APIs for managing bids from members")
public class MemberBidController {

    private final BidService bidService;

    @Operation(summary = "Get all user bids", description = "Retrieve all bids made by the authenticated user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved all user bids", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PageResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping
    public ResponseEntity<?> getUserAllBids(
            @PageableDefault Pageable pageable
    ) {
        return Response.renderJSON(
                new PageResponse<>(bidService.getAllByUserId(pageable)),
                "Success Get Bids",
                HttpStatus.OK
        );
    }

    @Operation(summary = "Create a new bid", description = "Create a new bid based on the request details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created the bid", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BidResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid bid request", content = @Content)
    })
    @PostMapping
    public ResponseEntity<?> createBid(
            @Valid @RequestBody BidRequest request
    ) {
        return Response.renderJSON(
                bidService.create(request),
                "Success Create Bid",
                HttpStatus.CREATED
        );
    }

    @Operation(summary = "Get bid by ID", description = "Retrieve a specific bid by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the bid", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BidResponse.class))),
            @ApiResponse(responseCode = "404", description = "Bid not found", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getBidById(
            @Parameter(description = "ID of the bid") @PathVariable Long id
    ) {
        return Response.renderJSON(
                bidService.getById(id),
                "Success Get Bid",
                HttpStatus.OK
        );
    }

    @Operation(summary = "Change bid status", description = "Update the status of a bid by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated bid status", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BidResponse.class))),
            @ApiResponse(responseCode = "404", description = "Bid not found", content = @Content)
    })
    @PatchMapping("/{id}/{status}")
    public ResponseEntity<?> changeBidStatus(
            @Parameter(description = "ID of the bid") @PathVariable Long id,
            @Parameter(description = "New status of the bid") @RequestParam String status
    ) {
        return Response.renderJSON(
                bidService.updateStatusById(id, status),
                "Success Change Bid Status",
                HttpStatus.OK
        );
    }

    @Operation(summary = "Get bids by post ID", description = "Retrieve all bids associated with a specific post.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved bids by post", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PageResponse.class))),
            @ApiResponse(responseCode = "404", description = "Post not found", content = @Content)
    })
    @GetMapping("/post/{postId}")
    public ResponseEntity<?> getBidsByPostId(
            @Parameter(description = "ID of the post") @PathVariable Long postId,
            @PageableDefault Pageable pageable
    ) {
        return Response.renderJSON(
                new PageResponse<>(bidService.getAllByPostId(postId, pageable)),
                "Success Get Bids by Post",
                HttpStatus.OK
        );
    }

    @Operation(summary = "Delete bid by ID", description = "Delete a bid by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted the bid", content = @Content),
            @ApiResponse(responseCode = "404", description = "Bid not found", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBid(
            @Parameter(description = "ID of the bid") @PathVariable Long id
    ) {
        bidService.deleteById(id);
        return Response.renderJSON(
                id,
                "Success Delete Bid"
        );
    }
}
