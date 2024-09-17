package com.palu_gada_be.palu_gada_be.dto.response;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewResponse {
    private Long id;
    private UserResponse user;
    private PostResponse post;
    private Long rating;
    private String comment;
}
