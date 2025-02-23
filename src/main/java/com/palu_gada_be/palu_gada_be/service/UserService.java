package com.palu_gada_be.palu_gada_be.service;

import com.palu_gada_be.palu_gada_be.dto.request.RegisterRequest;
import com.palu_gada_be.palu_gada_be.dto.request.ResetPasswordRequest;
import com.palu_gada_be.palu_gada_be.dto.request.UserUpdateRequest;
import com.palu_gada_be.palu_gada_be.dto.response.UserResponse;
import com.palu_gada_be.palu_gada_be.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {
    Page<UserResponse> getAll(String nameLikeFilter, List<Long> districtIds, String sortField, String sortDirection, Pageable pageable);
    UserResponse getById(Long id);
    User findById(Long id);
    UserResponse createAdmin(RegisterRequest request);
    UserResponse updateById(UserUpdateRequest updatedUser, MultipartFile file);
    User updateBalance(Long id, Long amount);
    User getAuthentication();
    User resetPassword(ResetPasswordRequest request);
    void deleteById(Long id);
}
