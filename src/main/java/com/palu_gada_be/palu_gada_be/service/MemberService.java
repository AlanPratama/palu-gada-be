package com.palu_gada_be.palu_gada_be.service;

import com.palu_gada_be.palu_gada_be.dto.request.ChangePasswordRequest;
import com.palu_gada_be.palu_gada_be.dto.request.ResetPasswordRequest;
import com.palu_gada_be.palu_gada_be.model.User;

public interface MemberService {
    User getAuthentication();
    User resetPassword(ResetPasswordRequest request);
    User changePassword(ChangePasswordRequest request);
}
