package com.palu_gada_be.palu_gada_be.mapper;

import com.palu_gada_be.palu_gada_be.dto.response.PostCategoryResponse;
import com.palu_gada_be.palu_gada_be.model.PostCategory;
import com.palu_gada_be.palu_gada_be.util.DateTimeUtil;
import lombok.Data;

@Data
public class PostCategoryMapper {
    public static PostCategoryResponse toPostCategoryResponse(PostCategory postCategory){
        return PostCategoryResponse.builder()
                .id(postCategory.getCategory().getId())
                .category(postCategory.getCategory().getName())
                .createdAt(DateTimeUtil.convertLocalDateTimeToString(postCategory.getCreatedAt(), "yyyy-MM-dd HH:mm:ss"))
                .updatedAt(DateTimeUtil.convertLocalDateTimeToString(postCategory.getUpdatedAt(), "yyyy-MM-dd HH:mm:ss"))
                .build();
    }
}
