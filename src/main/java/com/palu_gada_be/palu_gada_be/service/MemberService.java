package com.palu_gada_be.palu_gada_be.service;

import com.palu_gada_be.palu_gada_be.dto.request.user.ResetPasswordRequest;
import com.palu_gada_be.palu_gada_be.model.User;

public interface MemberService {
    User getAuthentication();
    User resetPassword(ResetPasswordRequest request);
}
