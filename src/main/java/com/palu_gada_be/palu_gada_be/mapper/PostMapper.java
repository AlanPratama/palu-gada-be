package com.palu_gada_be.palu_gada_be.mapper;

import com.palu_gada_be.palu_gada_be.dto.response.PostResponse;
import com.palu_gada_be.palu_gada_be.model.Post;
import com.palu_gada_be.palu_gada_be.util.DateTimeUtil;
import lombok.Data;

import java.util.stream.Collectors;

@Data
public class PostMapper {
    public static PostResponse toPostResponse(Post post) {
        return PostResponse.builder()
                .id(post.getId())
                .user(UserMapper.toUserResponse(post.getUser()))
                .district(DistrictMapper.toDistrictResponse(post.getDistrict()))
                .title(post.getTitle())
                .description(post.getDescription())
                .budgetMin(post.getBudgetMin())
                .budgetMax(post.getBudgetMax())
                .deadline(post.getPostDeadline().toString())
                .finishDay(post.getFinishDay())
                .status(post.getPostStatus().toString())
                .isUrgent(post.getIsUrgent())
                .imageUrl(post.getImageUrl())
                .postCategories(post.getPostCategories().stream().map(PostCategoryMapper::toPostCategoryResponse).collect(Collectors.toList()))
                .bids(post.getBids())
                .createdAt(DateTimeUtil.convertLocalDateTimeToString(post.getCreatedAt(), "yyyy-MM-dd HH:mm:ss"))
                .updatedAt(DateTimeUtil.convertLocalDateTimeToString(post.getUpdatedAt(), "yyyy-MM-dd HH:mm:ss"))
                .build();
    }
}
