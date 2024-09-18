package com.palu_gada_be.palu_gada_be.mapper;

import com.palu_gada_be.palu_gada_be.dto.response.UserReportResponse;
import com.palu_gada_be.palu_gada_be.model.UserReport;
import com.palu_gada_be.palu_gada_be.util.DateTimeUtil;

public class UserReportMapper {
    public static UserReportResponse toUserReportResponse (UserReport userReport) {
        if (userReport == null) {
            return null;
        }

        return UserReportResponse.builder()
                .id(userReport.getId())
                .reportedUser(UserMapper.toUserResponse(userReport.getUserReported()))
                .user(UserMapper.toUserResponse(userReport.getUser()))
                .message(userReport.getMessage())
                .createdAt(DateTimeUtil.convertLocalDateTimeToString(userReport.getCreatedAt(), "yyyy-MM-dd HH:mm:ss"))
                .updatedAt(DateTimeUtil.convertLocalDateTimeToString(userReport.getUpdatedAt(), "yyyy-MM-dd HH:mm:ss"))
                .build();
    }
}
