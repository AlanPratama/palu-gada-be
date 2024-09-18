package com.palu_gada_be.palu_gada_be.dto.response;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationResponse {
    private Long id;
    private UserResponse user;
    private String title;
    private String description;
    private Boolean isRead;
    private String icon;
    private String createdAt;
    private String updatedAt;
}
