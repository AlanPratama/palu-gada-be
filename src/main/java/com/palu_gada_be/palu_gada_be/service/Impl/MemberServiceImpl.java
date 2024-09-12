package com.palu_gada_be.palu_gada_be.service.Impl;

import com.palu_gada_be.palu_gada_be.dto.request.user.ResetPasswordRequest;
import com.palu_gada_be.palu_gada_be.model.User;
import com.palu_gada_be.palu_gada_be.repository.UserRepository;
import com.palu_gada_be.palu_gada_be.security.JwtService;
import com.palu_gada_be.palu_gada_be.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User getAuthentication() {
        return userRepository.findById(jwtService.getUserAuthenticated().getId()).orElseThrow(
                () -> new RuntimeException("Something went wrong")
        );
    }

    @Override
    public User resetPassword(ResetPasswordRequest request) {
        User userAuthenticated = getAuthentication();
        if (!(request.getPassword().equals(request.getPasswordConfirm()))){
            throw new RuntimeException("Password not match");
        }

        userAuthenticated.setPassword(passwordEncoder.encode(request.getPassword()));

        return userRepository.save(userAuthenticated);
    }
}
