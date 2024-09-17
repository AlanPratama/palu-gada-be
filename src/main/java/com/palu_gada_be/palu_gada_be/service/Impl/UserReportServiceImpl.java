package com.palu_gada_be.palu_gada_be.service.Impl;

import com.palu_gada_be.palu_gada_be.dto.request.UserReportRequest;
import com.palu_gada_be.palu_gada_be.dto.response.UserReportResponse;
import com.palu_gada_be.palu_gada_be.mapper.UserMapper;
import com.palu_gada_be.palu_gada_be.mapper.UserReportMapper;
import com.palu_gada_be.palu_gada_be.model.User;
import com.palu_gada_be.palu_gada_be.model.UserReport;
import com.palu_gada_be.palu_gada_be.repository.UserReportRepository;
import com.palu_gada_be.palu_gada_be.repository.UserRepository;
import com.palu_gada_be.palu_gada_be.security.JwtService;
import com.palu_gada_be.palu_gada_be.service.UserReportService;
import com.palu_gada_be.palu_gada_be.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserReportServiceImpl implements UserReportService {

    private final UserReportRepository userReportRepository;
    private final UserService userService;
    private final JwtService jwtService;

    @Override
    public UserReportResponse create(UserReportRequest request) {
        User reportedUser = userService.findById(request.getUserId());
        User user = jwtService.getUserAuthenticated();

        if (reportedUser.getId().equals(user.getId())){
            throw new RuntimeException("You cannot report your own account");
        }

        UserReport userReport = UserReport.builder()
                .userReported(reportedUser)
                .user(user)
                .message(request.getMessage())
                .build();

        UserReport savedUserReport = userReportRepository.save(userReport);

        return UserReportMapper.toUserReportResponse(savedUserReport);
    }

    @Override
    public Page<UserReportResponse> getAll(Pageable pageable) {
        Page<UserReport> userReports = userReportRepository.findAll(pageable);

        return userReports.map(UserReportMapper::toUserReportResponse);
    }

    @Override
    public Page<UserReportResponse> getByUserId(Pageable pageable) {
        Long id = jwtService.getUserAuthenticated().getId();
        Page<UserReport> userReports = userReportRepository.findByUserId(id, pageable);

        return userReports.map(UserReportMapper::toUserReportResponse);
    }

    @Override
    public UserReport findById(Long id) {
        return userReportRepository.findById(id).orElseThrow(
                () -> new RuntimeException("User Report not found")
        );
    }

    @Override
    public UserReportResponse getById(Long id) {
        UserReport userReport = findById(id);

        return UserReportMapper.toUserReportResponse(userReport);
    }
}
