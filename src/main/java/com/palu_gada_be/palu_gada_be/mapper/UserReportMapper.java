package com.palu_gada_be.palu_gada_be.mapper;

import com.palu_gada_be.palu_gada_be.dto.response.UserReportResponse;
import com.palu_gada_be.palu_gada_be.model.UserReport;

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
                .build();
    }
}
