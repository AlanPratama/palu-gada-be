package com.palu_gada_be.palu_gada_be.dto.request;

import lombok.*;

import java.util.List;

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
    private List<Long> categoriesId;
    private Long finishDay;
    private Boolean isUrgent;
    private String imageUrl;
}
