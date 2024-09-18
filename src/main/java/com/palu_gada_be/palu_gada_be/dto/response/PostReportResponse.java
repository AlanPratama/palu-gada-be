package com.palu_gada_be.palu_gada_be.dto.response;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostReportResponse {
    private Long id;
    private UserResponse user;
    private PostResponse post;
    private String message;
}
