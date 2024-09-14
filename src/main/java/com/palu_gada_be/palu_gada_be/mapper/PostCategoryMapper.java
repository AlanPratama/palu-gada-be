package com.palu_gada_be.palu_gada_be.mapper;

import com.palu_gada_be.palu_gada_be.dto.response.PostCategoryResponse;
import com.palu_gada_be.palu_gada_be.model.PostCategory;
import lombok.Data;

@Data
public class PostCategoryMapper {
    public static PostCategoryResponse toPostCategoryResponse(PostCategory postCategory){
        return PostCategoryResponse.builder()
                .id(postCategory.getId())
                .category(postCategory.getCategory().getName())
                .build();
    }
}
