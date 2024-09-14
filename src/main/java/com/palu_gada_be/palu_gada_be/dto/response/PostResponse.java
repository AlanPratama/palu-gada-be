package com.palu_gada_be.palu_gada_be.dto.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostResponse {
    private Long id;
    private UserResponse user;
    private DistrictResponse district;
    private String title;
    private String description;
    private Long budgetMin;
    private Long budgetMax;
    private String deadline;
    private Long finishDay;
    private String status;
    private Boolean isUrgent;
    private String imageUrl;
    private List<PostCategoryResponse> postCategories;
}
