package com.palu_gada_be.palu_gada_be.service;

import com.palu_gada_be.palu_gada_be.dto.request.RegisterRequest;
import com.palu_gada_be.palu_gada_be.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    Page<User> getAll(Pageable pageable);
    User getById(Long id);
    User createAdmin(RegisterRequest request);
    User updateById(Long id, User request);
    void deleteById(Long id);
}
