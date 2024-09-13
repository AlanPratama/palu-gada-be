package com.palu_gada_be.palu_gada_be.service.Impl;

import com.palu_gada_be.palu_gada_be.constant.PostStatus;
import com.palu_gada_be.palu_gada_be.dto.request.post.PostRequest;
import com.palu_gada_be.palu_gada_be.model.District;
import com.palu_gada_be.palu_gada_be.model.Post;
import com.palu_gada_be.palu_gada_be.model.User;
import com.palu_gada_be.palu_gada_be.repository.PostRepository;
import com.palu_gada_be.palu_gada_be.security.JwtService;
import com.palu_gada_be.palu_gada_be.service.DistrictService;
import com.palu_gada_be.palu_gada_be.service.PostService;
import com.palu_gada_be.palu_gada_be.util.DateTimeUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final DistrictService districtService;
    private final JwtService jwtService;

    @Override
    public Post create(PostRequest request) {
        User user = jwtService.getUserAuthenticated();
        District district = districtService.getById(request.getDistrictId());

        LocalDateTime deadline;
        if (request.getDeadline() != null && !request.getDeadline().isEmpty()) {
            deadline = DateTimeUtil.convertStringToLocalDateTime(request.getDeadline(), "yyyy-MM-dd HH:mm:ss");
        } else {
            deadline = LocalDateTime.now().plus(30, ChronoUnit.DAYS);
        }

        Post newPost = Post.builder()
                .user(user)
                .district(district)
                .title(request.getTitle())
                .description(request.getDescription())
                .budgetMin(request.getBudgetMin())
                .budgetMax(request.getBudgetMax())
                .postDeadline(deadline)
                .finishDay(request.getFinishDay())
                .postStatus(PostStatus.AVAILABLE)
                .imageUrl(request.getImageUrl())
                .build();

        return postRepository.save(newPost);
    }

    @Override
    public Page<Post> getAll(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    @Override
    public Page<Post> getAllByUserId(Pageable pageable) {
        Long id = jwtService.getUserAuthenticated().getId();
        return postRepository.findByUserId(id, pageable);
    }

    @Override
    public Post getById(Long id) {
        return postRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Post not Found")
        );
    }

    @Override
    public Post updateById(Long id, PostRequest request) {
        District newDistrict = districtService.getById(id);

        Post updatePost = Post.builder()
                .district(newDistrict)
                .title(request.getTitle())
                .description(request.getDescription())
                .budgetMin(request.getBudgetMin())
                .budgetMax(request.getBudgetMax())
                .finishDay(request.getFinishDay())
                .postStatus(PostStatus.valueOf(request.getStatus()))
                .imageUrl(request.getImageUrl())
                .build();

        return postRepository.save(updatePost);
    }

    @Override
    public void deleteById(Long id) {
        postRepository.delete(getById(id));
    }
}
