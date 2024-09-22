package com.palu_gada_be.palu_gada_be.dto.response;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserReportResponse {
    private Long id;
    private UserResponse reportedUser;
    private UserResponse user;
    private String message;
    private String createdAt;
    private String updatedAt;
}
