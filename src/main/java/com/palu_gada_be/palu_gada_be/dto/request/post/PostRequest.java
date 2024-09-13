package com.palu_gada_be.palu_gada_be.dto.request.post;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostRequest {
    private Long districtId;
    private String title;
    private String description;
    private Long budgetMin;
    private Long budgetMax;
    private String deadline;
    private Long finishDay;
    private String status;
    private String imageUrl;
}
