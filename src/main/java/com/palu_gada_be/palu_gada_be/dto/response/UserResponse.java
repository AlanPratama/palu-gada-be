package com.palu_gada_be.palu_gada_be.dto.response;

import com.palu_gada_be.palu_gada_be.constant.UserGender;
import com.palu_gada_be.palu_gada_be.model.UserCategory;
import lombok.*;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {
    private Long id;
    private Long districtId;
    private String name;
    private String email;
    private String phone;
    private String username;
    private String photoUrl;
    private UserGender userGender;
    private List<UserCategory> userCategories;
    private String createdAt;
    private String updatedAt;
}
