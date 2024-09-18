package com.palu_gada_be.palu_gada_be.service.Impl;

import com.palu_gada_be.palu_gada_be.dto.request.PostReportRequest;
import com.palu_gada_be.palu_gada_be.dto.response.PostReportResponse;
import com.palu_gada_be.palu_gada_be.mapper.PostMapper;
import com.palu_gada_be.palu_gada_be.mapper.PostReportMapper;
import com.palu_gada_be.palu_gada_be.mapper.UserMapper;
import com.palu_gada_be.palu_gada_be.model.Post;
import com.palu_gada_be.palu_gada_be.model.PostReport;
import com.palu_gada_be.palu_gada_be.model.User;
import com.palu_gada_be.palu_gada_be.repository.PostReportRepository;
import com.palu_gada_be.palu_gada_be.security.JwtService;
import com.palu_gada_be.palu_gada_be.service.PostReportService;
import com.palu_gada_be.palu_gada_be.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostReportServiceImpl implements PostReportService {

    private final PostReportRepository postReportRepository;
    private final PostService postService;
    private final JwtService jwtService;

    @Override
    public PostReportResponse create(PostReportRequest request) {
        User user = jwtService.getUserAuthenticated();
        Post post = postService.findById(request.getPostId());

        PostReport postReport = PostReport.builder()
                .user(user)
                .post(post)
                .message(request.getMessage())
                .build();

        PostReport createdPostReport = postReportRepository.save(postReport);

        return PostReportMapper.toPaymentResponse(createdPostReport);
    }

    @Override
    public Page<PostReportResponse> getAll(Pageable pageable) {
        Page<PostReport> postReports = postReportRepository.findAll(pageable);

        return postReports.map(PostReportMapper::toPaymentResponse);
    }

    @Override
    public Page<PostReportResponse> getByUserId(Long id, Pageable pageable) {
        Page<PostReport> postReports = postReportRepository.findByUserId(id, pageable);

        return postReports.map(PostReportMapper::toPaymentResponse);
    }

    @Override
    public Page<PostReportResponse> getByPostId(Long id, Pageable pageable) {
        Page<PostReport> postReports = postReportRepository.findByPostId(id, pageable);

        return postReports.map(PostReportMapper::toPaymentResponse);
    }

    @Override
    public Page<PostReportResponse> getByUserAuthenticated(Pageable pageable) {
        Long id = jwtService.getUserAuthenticated().getId();

        return this.getByUserId(id, pageable);
    }

    @Override
    public PostReport findById(Long id) {
        return postReportRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Post Report not found")
        );
    }

    @Override
    public PostReportResponse getById(Long id) {
        PostReport postReport = this.findById(id);

        return PostReportMapper.toPaymentResponse(postReport);
    }

    @Override
    public PostReportResponse update(Long id, PostReportRequest request) {


        return null;
    }
}
