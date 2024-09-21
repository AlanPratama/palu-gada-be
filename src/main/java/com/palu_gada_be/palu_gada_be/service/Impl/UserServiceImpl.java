package com.palu_gada_be.palu_gada_be.service.Impl;

import com.palu_gada_be.palu_gada_be.constant.UserGender;
import com.palu_gada_be.palu_gada_be.dto.request.RegisterRequest;
import com.palu_gada_be.palu_gada_be.dto.request.ResetPasswordRequest;
import com.palu_gada_be.palu_gada_be.dto.request.UserUpdateRequest;
import com.palu_gada_be.palu_gada_be.dto.response.CloudinaryResponse;
import com.palu_gada_be.palu_gada_be.dto.response.UserResponse;
import com.palu_gada_be.palu_gada_be.mapper.UserMapper;
import com.palu_gada_be.palu_gada_be.model.*;
import com.palu_gada_be.palu_gada_be.repository.RoleRepository;
import com.palu_gada_be.palu_gada_be.repository.UserCategoryRepository;
import com.palu_gada_be.palu_gada_be.repository.UserRepository;
import com.palu_gada_be.palu_gada_be.security.JwtService;
import com.palu_gada_be.palu_gada_be.service.*;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.palu_gada_be.palu_gada_be.specification.UserSpecification.*;

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
    private final UserCategoryRepository userCategoryRepository;

    @Override
    public Page<UserResponse> getAll(String nameLikeFilter, List<Long> districtIds, String sortField, String sortDirection, Pageable pageable) {
        Specification<User> spec = Specification.where(StringUtils.isBlank(nameLikeFilter) ? null : nameLike(nameLikeFilter))
                .and(CollectionUtils.isEmpty(districtIds) ? null : inDistrict(districtIds))
                .and(StringUtils.isBlank(sortField) ? null : sortByField(sortField, sortDirection));

        Page<User> users = userRepository.findAll(spec, pageable);
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
    public UserResponse updateById(UserUpdateRequest updatedUser, MultipartFile file) {
        User user = jwtService.getUserAuthenticated();

        if (updatedUser.getDistrictId() != null) {
            District district = districtService.getById(updatedUser.getDistrictId());
            user.setDistrict(district);
        }

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

        if (updatedUser.getUserCategoriesId() != null) {
            List<Long> existingCategoryIds = user.getUserCategories().stream()
                    .map(userCategory -> userCategory.getCategory().getId())
                    .collect(Collectors.toList());

            List<UserCategory> categoriesToRemove = user.getUserCategories().stream()
                    .filter(uc -> !updatedUser.getUserCategoriesId().contains(uc.getCategory().getId()))
                    .collect(Collectors.toList());

            user.getUserCategories().removeAll(categoriesToRemove);
            userCategoryRepository.deleteAll(categoriesToRemove);

            List<UserCategory> categoriesToAdd = updatedUser.getUserCategoriesId().stream()
                    .filter(categoryId -> !existingCategoryIds.contains(categoryId))
                    .map(categoryId -> UserCategory.builder()
                            .category(categoryService.getById(categoryId))
                            .user(user)
                            .build())
                    .collect(Collectors.toList());

            userCategoyService.createAll(categoriesToAdd);
            user.getUserCategories().addAll(categoriesToAdd);
        }

        if (updatedUser.getPhone() != null) {
            user.setPhone(updatedUser.getPhone());
        }

        if (updatedUser.getAbout() != null) {
            user.setAbout(updatedUser.getAbout());
        }

        if (updatedUser.getAddress() != null) {
            user.setAddress(updatedUser.getAddress());
        }

        if (updatedUser.getName() != null) {
            user.setName(updatedUser.getName());
        }

        if (updatedUser.getNik() != null) {
            user.setNik(updatedUser.getNik());
        }

        if (updatedUser.getBankAccount() != null) {
            user.setBankAccount(updatedUser.getBankAccount());
        }

        if (updatedUser.getBirthDate() != null) {
            user.setBirthDate(updatedUser.getBirthDate());
        }

        if (updatedUser.getUserGender() != null) {
            user.setUserGender(UserGender.valueOf(updatedUser.getUserGender().toUpperCase()));
        }

        User updated = userRepository.save(user);
        return UserMapper.toUserResponse(updated);
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public User updateBalance(Long id, Long amount) {
        User user = findById(id);
        user.setBalance(user.getBalance() + amount);
        userRepository.save(user);
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
