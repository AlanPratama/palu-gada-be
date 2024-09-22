package com.palu_gada_be.palu_gada_be.mapper;

import com.palu_gada_be.palu_gada_be.dto.response.DistrictResponse;
import com.palu_gada_be.palu_gada_be.model.District;
import com.palu_gada_be.palu_gada_be.util.DateTimeUtil;
import lombok.Data;

@Data
public class DistrictMapper {
    public static DistrictResponse toDistrictResponse(District district) {
        return DistrictResponse.builder()
                .id(district.getId())
                .districtName(district.getDistrictName())
                .regency(district.getRegency())
                .province(district.getProvince())
                .createdAt(DateTimeUtil.convertLocalDateTimeToString(district.getCreatedAt(), "yyyy-MM-dd HH:mm:ss"))
                .updatedAt(DateTimeUtil.convertLocalDateTimeToString(district.getUpdatedAt(), "yyyy-MM-dd HH:mm:ss"))
                .build();
    }
}
