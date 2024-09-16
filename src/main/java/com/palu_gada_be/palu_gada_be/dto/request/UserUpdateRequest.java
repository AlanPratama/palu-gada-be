package com.palu_gada_be.palu_gada_be.dto.request;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateRequest {
    private Long districtId;
    private String phone;
    private String address;
    private String name;
    private String birthDate;
    private String userGender;
}
