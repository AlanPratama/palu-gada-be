package com.palu_gada_be.palu_gada_be.mapper;

import com.palu_gada_be.palu_gada_be.dto.response.UserResponse;
import com.palu_gada_be.palu_gada_be.model.User;
import com.palu_gada_be.palu_gada_be.util.DateTimeUtil;
import lombok.Data;

@Data
public class UserMapper {
    public static UserResponse toUserResponse(User user) {
        if (user == null) {
            return null; // Handle null user
        }

        return UserResponse.builder()
                .id(user.getId())
                .districtId(user.getDistrict() != null ? user.getDistrict().getId() : null)
                .name(user.getName())
                .email(user.getEmail())
                .username(user.getUsername())
                .photoUrl(user.getPhotoUrl())
                .nik(user.getNik())
                .phone(user.getPhone())
                .about(user.getAbout())
                .bankAccount(user.getBankAccount())
                .userGender(user.getUserGender())
                .userCategories(user.getUserCategories())
                .createdAt(DateTimeUtil.convertLocalDateTimeToString(user.getCreatedAt(), "yyyy-MM-dd HH:mm:ss"))
                .updatedAt(DateTimeUtil.convertLocalDateTimeToString(user.getUpdatedAt(), "yyyy-MM-dd HH:mm:ss"))
                .build();
    }
}
