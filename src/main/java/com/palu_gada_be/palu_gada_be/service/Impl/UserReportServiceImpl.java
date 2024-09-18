package com.palu_gada_be.palu_gada_be.service.Impl;

import com.palu_gada_be.palu_gada_be.dto.request.UserReportRequest;
import com.palu_gada_be.palu_gada_be.dto.response.UserReportResponse;
import com.palu_gada_be.palu_gada_be.mapper.UserReportMapper;
import com.palu_gada_be.palu_gada_be.model.User;
import com.palu_gada_be.palu_gada_be.model.UserReport;
import com.palu_gada_be.palu_gada_be.repository.UserReportRepository;
import com.palu_gada_be.palu_gada_be.security.JwtService;
import com.palu_gada_be.palu_gada_be.service.UserReportService;
import com.palu_gada_be.palu_gada_be.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

import static com.palu_gada_be.palu_gada_be.specification.UserReportSpecification.*;

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
    public Page<UserReportResponse> getAll(String messageLikeFilter, List<Long> userReportedIds, List<Long> userReportIds, String sortField, String sortDirection, Pageable pageable) {
        Specification<UserReport> spec = Specification.where(StringUtils.isBlank(messageLikeFilter) ? null : messageLike(messageLikeFilter))
                .and(CollectionUtils.isEmpty(userReportedIds) ? null : inUserReported(userReportedIds))
                .and(CollectionUtils.isEmpty(userReportIds) ? null : inUserReport(userReportIds))
                .and(StringUtils.isBlank(sortField) ? null : sortByField(sortField, sortDirection));

        Page<UserReport> userReports = userReportRepository.findAll(spec, pageable);

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
