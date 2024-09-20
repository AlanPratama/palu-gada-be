package com.palu_gada_be.palu_gada_be.controller.Admin;

import com.palu_gada_be.palu_gada_be.constant.ConstantEndpoint;
import com.palu_gada_be.palu_gada_be.dto.response.BidResponse;
import com.palu_gada_be.palu_gada_be.service.BidService;
import com.palu_gada_be.palu_gada_be.util.PageResponse;
import com.palu_gada_be.palu_gada_be.util.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ConstantEndpoint.ADMIN_BID_API)
@RequiredArgsConstructor
@Tag(name = "Admin Bid API", description = "APIs untuk mengelola lelang oleh admin")
@SecurityRequirement(name = "bearerAuth")
public class AdminBidController {

    private final BidService bidService;

    @Operation(summary = "Mendapatkan semua bid", description = "Mengambil semua bid berdasarkan filter yang tersedia.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sukses mendapatkan semua bid"),
            @ApiResponse(responseCode = "400", description = "Permintaan tidak valid"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Bearer token tidak valid")
    })
    @GetMapping
    public ResponseEntity<?> getAll(
            @RequestParam(required = false) String message,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String sortField,
            @RequestParam(required = false) String sortDirection,
            @PageableDefault Pageable pageable
    ) {
        PageResponse<BidResponse> response = new PageResponse<>(bidService.getAll(message, status, sortField, sortDirection, pageable));
        return Response.renderJSON(response, "Success Get Bids", HttpStatus.OK);
    }

    @Operation(summary = "Mendapatkan bid berdasarkan user ID", description = "Mengambil semua bid yang dibuat oleh user tertentu.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sukses mendapatkan bid"),
            @ApiResponse(responseCode = "400", description = "Permintaan tidak valid"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Bearer token tidak valid")
    })
    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUserAllBids(
            @PageableDefault Pageable pageable
    ) {
        PageResponse<BidResponse> response = new PageResponse<>(bidService.getAllByUserId(pageable));
        return Response.renderJSON(response, "Success Get Bids", HttpStatus.OK);
    }

    @Operation(summary = "Mendapatkan bid berdasarkan ID", description = "Mengambil detail bid berdasarkan ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sukses mendapatkan bid"),
            @ApiResponse(responseCode = "404", description = "Bid tidak ditemukan"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Bearer token tidak valid")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getBidById(@PathVariable @Parameter(description = "ID dari bid") Long id) {
        BidResponse response = bidService.getById(id);
        return Response.renderJSON(response, "Success Get Bid", HttpStatus.OK);
    }

    @Operation(summary = "Update status bid", description = "Mengubah status bid berdasarkan ID bid.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sukses mengubah status bid"),
            @ApiResponse(responseCode = "400", description = "Permintaan tidak valid"),
            @ApiResponse(responseCode = "404", description = "Bid tidak ditemukan"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Bearer token tidak valid")
    })
    @PatchMapping("/{id}/status")
    public ResponseEntity<?> updateBidStatus(
            @PathVariable @Parameter(description = "ID dari bid") Long id,
            @RequestParam @Parameter(description = "Status baru dari bid") String status
    ) {
        BidResponse response = bidService.updateStatusById(id, status);
        return Response.renderJSON(response, "Success Change Bid Status", HttpStatus.OK);
    }

    @Operation(summary = "Mendapatkan bid berdasarkan post ID", description = "Mengambil semua bid yang terkait dengan post tertentu berdasarkan post ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sukses mendapatkan bid"),
            @ApiResponse(responseCode = "400", description = "Permintaan tidak valid"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Bearer token tidak valid")
    })
    @GetMapping("/post/{postId}")
    public ResponseEntity<?> getBidsByPostId(
            @PathVariable @Parameter(description = "ID dari post") Long postId,
            @PageableDefault Pageable pageable
    ) {
        PageResponse<BidResponse> response = new PageResponse<>(bidService.getAllByPostId(postId, pageable));
        return Response.renderJSON(response, "Success Get Bids by Post", HttpStatus.OK);
    }

    @Operation(summary = "Menghapus bid berdasarkan ID", description = "Menghapus bid tertentu berdasarkan ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Sukses menghapus bid"),
            @ApiResponse(responseCode = "404", description = "Bid tidak ditemukan"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Bearer token tidak valid")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBidById(
            @PathVariable @Parameter(description = "ID dari bid") Long id
    ) {
        bidService.deleteById(id);
        return Response.renderJSON(null, "Success Delete Bid", HttpStatus.NO_CONTENT);
    }
}
