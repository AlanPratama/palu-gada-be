package com.palu_gada_be.palu_gada_be.mapper;

import com.palu_gada_be.palu_gada_be.dto.response.NotificationResponse;
import com.palu_gada_be.palu_gada_be.model.Notification;
import com.palu_gada_be.palu_gada_be.util.DateTimeUtil;
import lombok.Data;

@Data
public class NotificationMapper {
    public static NotificationResponse toNotificationResponse(Notification notification) {
        return NotificationResponse.builder()
                .id(notification.getId())
                .user(UserMapper.toUserResponse(notification.getUser()))
                .title(notification.getTitle())
                .description(notification.getTitle())
                .isRead(notification.getIsRead())
                .icon(notification.getIcon())
                .createdAt(DateTimeUtil.convertLocalDateTimeToString(notification.getCreatedAt(), "yyyy-MM-dd HH:mm:ss"))
                .updatedAt(DateTimeUtil.convertLocalDateTimeToString(notification.getUpdatedAt(), "yyyy-MM-dd HH:mm:ss"))
                .build();
    }
}
