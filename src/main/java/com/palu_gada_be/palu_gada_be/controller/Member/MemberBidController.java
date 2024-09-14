package com.palu_gada_be.palu_gada_be.controller.Member;

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
@RequestMapping(ConstantEndpoint.MEMBER_BID_API)
@RequiredArgsConstructor
public class MemberBidController {

    private final BidService bidService;

    @GetMapping
    public ResponseEntity<?> getUserAllBids(@PageableDefault Pageable pageable) {
        return Response.renderJSON(
                new PageResponse<>(bidService.getAllByUserId(pageable)),
                "Success Get Bids",
                HttpStatus.OK
        );
    }

    @PostMapping
    public ResponseEntity<?> createBid(@RequestBody BidRequest request) {
        return Response.renderJSON(
                bidService.create(request),
                "Success Create Bid",
                HttpStatus.CREATED
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

    @PatchMapping("/{id}/{status}")
    public ResponseEntity<?> changeBidStatus(@PathVariable Long id, @RequestParam String status) {
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
}
