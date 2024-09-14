package com.palu_gada_be.palu_gada_be.mapper;

import com.palu_gada_be.palu_gada_be.dto.response.UserResponse;
import com.palu_gada_be.palu_gada_be.model.User;
import lombok.Data;

@Data
public class UserMapper {
    public static UserResponse toUserResponse(User user) {
        if (user == null) {
            return null; // Handle null user
        }

        return UserResponse.builder()
                .id(user.getId())
                .districtId(user.getDistrict() != null ? user.getDistrict().getId() : null) // Handle potential null district
                .name(user.getName())
                .email(user.getEmail())
                .photoUrl(user.getPhotoUrl())
                .userGender(user.getUserGender())
                .build();
    }
}
