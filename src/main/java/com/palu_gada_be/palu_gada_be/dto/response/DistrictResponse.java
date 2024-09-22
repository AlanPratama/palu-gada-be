package com.palu_gada_be.palu_gada_be.dto.response;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DistrictResponse {
    private Long id;
    private String districtName;
    private String regency;
    private String province;
    private String createdAt;
    private String updatedAt;
}
