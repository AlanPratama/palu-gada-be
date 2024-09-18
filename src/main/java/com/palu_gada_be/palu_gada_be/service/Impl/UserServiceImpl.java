package com.palu_gada_be.palu_gada_be.service.Impl;

import com.palu_gada_be.palu_gada_be.constant.UserGender;
import com.palu_gada_be.palu_gada_be.dto.request.RegisterRequest;
import com.palu_gada_be.palu_gada_be.dto.request.ResetPasswordRequest;
import com.palu_gada_be.palu_gada_be.dto.request.UpdateBalanceRequest;
import com.palu_gada_be.palu_gada_be.dto.request.UserUpdateRequest;
import com.palu_gada_be.palu_gada_be.dto.response.CloudinaryResponse;
import com.palu_gada_be.palu_gada_be.dto.response.UserResponse;
import com.palu_gada_be.palu_gada_be.mapper.UserMapper;
import com.palu_gada_be.palu_gada_be.model.*;
import com.palu_gada_be.palu_gada_be.repository.RoleRepository;
import com.palu_gada_be.palu_gada_be.repository.UserRepository;
import com.palu_gada_be.palu_gada_be.security.JwtService;
import com.palu_gada_be.palu_gada_be.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final CloudinaryService cloudinaryService;
    private final CategoryService categoryService;
    private final UserCategoyService userCategoyService;
    private final DistrictService districtService;

    @Override
    public Page<UserResponse> getAll(Pageable pageable) {
        Page<User> users = userRepository.findAll(pageable);
        return users.map(UserMapper::toUserResponse);
    }

    @Override
    public UserResponse getById(Long id) {
        User user = userRepository.findById(id).orElseThrow(
            () -> new RuntimeException("Post not Found")
        );

        return UserMapper.toUserResponse(user);
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(
            () -> new RuntimeException("User Not Found")
        );
    }

    @Override
    public UserResponse createAdmin(RegisterRequest request) {
        if (userRepository.findByUsernameOrEmail(request.getUsername(), request.getEmail()).isPresent()) {
            throw new RuntimeException("User already exist");
        }

        Role userRole = roleRepository.findByName("ROLE_ADMIN")
                .orElseGet(() -> {
                    Role role = new Role();
                    role.setName("ROLE_ADMIN");
                    return roleRepository.save(role);
                });

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(new HashSet<>(Set.of(userRole)))
                .build();

        User newCreatedAdmin = userRepository.save(user);
        return UserMapper.toUserResponse(newCreatedAdmin);
    }

    @Override
    @Transactional
    public UserResponse updateById(Long id, UserUpdateRequest updatedUser, MultipartFile file) {
        User user = findById(id);
        District district = districtService.getById(updatedUser.getDistrictId());

        if (file != null && !file.isEmpty()) {
            try {
                CloudinaryResponse response;

                if (user.getCloudinaryPublicId() != null && !user.getCloudinaryPublicId().isEmpty()) {
                    response = cloudinaryService.replaceFile(user.getCloudinaryPublicId(), file);
                } else {
                    response = cloudinaryService.uploadFile(file);
                }

                user.setPhotoUrl(response.getUrl());
                user.setCloudinaryPublicId(response.getPublicId());

            } catch (IOException e) {
                throw new RuntimeException("Failed to upload or replace image", e);
            }
        }

        List<UserCategory> userCategories = new ArrayList<>();

        try {
            if (updatedUser.getUserCategoriesId() != null){
                for (var c : updatedUser.getUserCategoriesId()){
                    UserCategory temp = UserCategory.builder()
                            .category(categoryService.getById(c))
                            .user(user)
                            .build();
                    userCategories.add(temp);
                }
                userCategoyService.createAll(userCategories);
            }
        } catch (Exception ex){
            throw new RuntimeException("Categories not found");
        }

        user.setDistrict(district);
        user.setPhone(updatedUser.getPhone());
        user.setAddress(updatedUser.getAddress());
        user.setName(updatedUser.getName());
        user.setBirthDate(updatedUser.getBirthDate());
        user.setUserGender(UserGender.valueOf(updatedUser.getUserGender().toUpperCase()));

        User updated = userRepository.save(user);
        return UserMapper.toUserResponse(updated);
    }

    @Override
    @Transactional
    public User updateBalance(Long id, Long amount) {
        User user = findById(id);
        try {
            user.setBalance(user.getBalance() + amount);
            userRepository.save(user);
        } catch (Exception ex){
            throw ex;
        }
        return user;
    }

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

    @Override
    public void deleteById(Long id) {
        userRepository.delete(findById(id));
    }
}
