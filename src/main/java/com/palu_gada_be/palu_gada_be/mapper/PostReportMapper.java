package com.palu_gada_be.palu_gada_be.mapper;


import com.palu_gada_be.palu_gada_be.dto.response.PostReportResponse;
import com.palu_gada_be.palu_gada_be.model.PostReport;

public class PostReportMapper {
    public static PostReportResponse toPostReportResponse(PostReport postReport){
        return PostReportResponse.builder()
                .id(postReport.getId())
                .user(UserMapper.toUserResponse(postReport.getUser()))
                .post(PostMapper.toPostResponse(postReport.getPost()))
                .message(postReport.getMessage())
                .build();
    }
}
