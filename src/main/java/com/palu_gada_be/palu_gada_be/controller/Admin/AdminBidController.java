package com.palu_gada_be.palu_gada_be.controller.Admin;

import com.palu_gada_be.palu_gada_be.constant.ConstantEndpoint;
import com.palu_gada_be.palu_gada_be.dto.request.BidRequest;
import com.palu_gada_be.palu_gada_be.service.BidService;
import com.palu_gada_be.palu_gada_be.util.PageResponse;
import com.palu_gada_be.palu_gada_be.util.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(ConstantEndpoint.ADMIN_BID_API)
@RequiredArgsConstructor
public class AdminBidController {

    private final BidService bidService;

    @GetMapping
    public ResponseEntity<?> getAll(@PageableDefault Pageable pageable) {
        return Response.renderJSON(
                new PageResponse<>(bidService.getAll(pageable)),
                "Success Get Bids",
                HttpStatus.OK
        );
    }
    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUserAllBids(@PageableDefault Pageable pageable) {
        return Response.renderJSON(
                new PageResponse<>(bidService.getAllByUserId(pageable)),
                "Success Get Bids",
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBidById(@PathVariable Long id) {
        return Response.renderJSON(
                bidService.getById(id),
                "Success Get Bid",
                HttpStatus.OK
        );
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<?> updateBidStatus(@PathVariable Long id, @RequestParam String status) {
        return Response.renderJSON(
                bidService.changeStatusById(id, status),
                "Success Change Bid Status",
                HttpStatus.OK
        );
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<?> getBidsByPostId(@PathVariable Long postId, @PageableDefault Pageable pageable) {
        return Response.renderJSON(
                new PageResponse<>(bidService.getAllByPostId(postId, pageable)),
                "Success Get Bids by Post",
                HttpStatus.OK
        );
    }

    // Endpoint to delete a bid by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBidById(@PathVariable Long id) {
        bidService.deleteById(id);
        return Response.renderJSON(
                null,
                "Success Delete Bid",
                HttpStatus.NO_CONTENT
        );
    }
}
