package com.palu_gada_be.palu_gada_be.mapper;

import com.palu_gada_be.palu_gada_be.dto.response.BidResponse;
import com.palu_gada_be.palu_gada_be.model.Bid;
import com.palu_gada_be.palu_gada_be.util.DateTimeUtil;
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
                .createdAt(DateTimeUtil.convertLocalDateTimeToString(bid.getCreatedAt(), "yyyy-MM-dd HH:mm:ss"))
                .updatedAt(DateTimeUtil.convertLocalDateTimeToString(bid.getUpdatedAt(), "yyyy-MM-dd HH:mm:ss"))
                .build();
    }
}
