package com.palu_gada_be.palu_gada_be.mapper;

import com.palu_gada_be.palu_gada_be.dto.response.ReviewResponse;
import com.palu_gada_be.palu_gada_be.model.Review;

public class ReviewMapper {
    public static ReviewResponse toReviewResponse(Review review){
        if (review == null){
            return null;
        }

        return ReviewResponse.builder()
                .id(review.getId())
                .user(UserMapper.toUserResponse(review.getUser()))
                .post(PostMapper.toPostResponse(review.getPost()))
                .rating(review.getRating())
                .comment(review.getComment())
                .build();
    }
}
