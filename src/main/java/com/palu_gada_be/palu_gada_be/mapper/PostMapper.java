package com.palu_gada_be.palu_gada_be.mapper;

import com.palu_gada_be.palu_gada_be.dto.response.PostResponse;
import com.palu_gada_be.palu_gada_be.model.Post;
import lombok.Data;

import java.util.stream.Collectors;

@Data
public class PostMapper {
    public static PostResponse toPostResponse(Post newPost) {
        return PostResponse.builder()
                .id(newPost.getId())
                .user(UserMapper.toUserResponse(newPost.getUser()))
                .district(DistrictMapper.toDistrictResponse(newPost.getDistrict()))
                .title(newPost.getTitle())
                .description(newPost.getDescription())
                .budgetMin(newPost.getBudgetMin())
                .budgetMax(newPost.getBudgetMax())
                .deadline(newPost.getPostDeadline().toString())
                .finishDay(newPost.getFinishDay())
                .status(newPost.getPostStatus().toString())
                .isUrgent(newPost.getIsUrgent())
                .imageUrl(newPost.getImageUrl())
                .postCategories(newPost.getPostCategories().stream().map(PostCategoryMapper::toPostCategoryResponse).collect(Collectors.toList()))
                .build();
    }
}
