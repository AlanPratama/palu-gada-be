package com.palu_gada_be.palu_gada_be.dto.response;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostCategoryResponse {
    private Long id;
    private String category;
    private String createdAt;
    private String updatedAt;
}
