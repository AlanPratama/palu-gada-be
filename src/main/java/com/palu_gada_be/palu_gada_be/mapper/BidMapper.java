package com.palu_gada_be.palu_gada_be.mapper;

import com.palu_gada_be.palu_gada_be.dto.response.BidResponse;
import com.palu_gada_be.palu_gada_be.model.Bid;
import lombok.Data;

@Data
public class BidMapper {
    public static BidResponse toBidResponse(Bid bid) {
        return BidResponse.builder()
                .id(bid.getId())
                .user(UserMapper.toUserResponse(bid.getUser()))
                .post(PostMapper.toPostResponse(bid.getPost()))
                .amount(bid.getAmount())
                .message(bid.getMessage())
                .status(bid.getBidStatus())
                .build();
    }
}
