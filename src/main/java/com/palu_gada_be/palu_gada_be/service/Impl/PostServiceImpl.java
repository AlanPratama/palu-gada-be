package com.palu_gada_be.palu_gada_be.service.Impl;

import com.palu_gada_be.palu_gada_be.constant.PostStatus;
import com.palu_gada_be.palu_gada_be.dto.request.PostRequest;
import com.palu_gada_be.palu_gada_be.dto.response.DistrictResponse;
import com.palu_gada_be.palu_gada_be.dto.response.PostResponse;
import com.palu_gada_be.palu_gada_be.dto.response.UserResponse;
import com.palu_gada_be.palu_gada_be.mapper.PostMapper;
import com.palu_gada_be.palu_gada_be.model.District;
import com.palu_gada_be.palu_gada_be.model.Post;
import com.palu_gada_be.palu_gada_be.model.PostCategory;
import com.palu_gada_be.palu_gada_be.model.User;
import com.palu_gada_be.palu_gada_be.repository.PostRepository;
import com.palu_gada_be.palu_gada_be.security.JwtService;
import com.palu_gada_be.palu_gada_be.service.CategoryService;
import com.palu_gada_be.palu_gada_be.service.DistrictService;
import com.palu_gada_be.palu_gada_be.service.PostCategoryService;
import com.palu_gada_be.palu_gada_be.service.PostService;
import com.palu_gada_be.palu_gada_be.util.DateTimeUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final DistrictService districtService;
    private final JwtService jwtService;
    private final PostCategoryService postCategoryService;
    private final CategoryService categoryService;

    @Override
    @Transactional
    public PostResponse create(PostRequest request) {
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

        Post post = postRepository.save(newPost);
        List<PostCategory> postCategories = new ArrayList<>();

        try {
            if (request.getCategoriesId() != null){
                for (var c : request.getCategoriesId()){
                    PostCategory temp = PostCategory.builder()
                            .category(categoryService.getById(c))
                            .post(post)
                            .build();
                    postCategories.add(temp);
                }
                postCategoryService.createAll(postCategories);
            }
        } catch (Exception ex){
            throw new RuntimeException("Error creating post");
        }

        newPost.setPostCategories(postCategories);
        postRepository.save(newPost);

        return PostMapper.toPostResponse(newPost);
    }

    @Override
    public Page<PostResponse> getAll(Pageable pageable) {
        Page<Post> posts = postRepository.findAll(pageable);

        return posts.map(PostMapper::toPostResponse);
    }

    @Override
    public Page<PostResponse> getAllByUserId(Pageable pageable) {
        Long id = jwtService.getUserAuthenticated().getId();
        Page<Post> posts = postRepository.findByUserId(id, pageable);

        return posts.map(PostMapper::toPostResponse);
    }

    @Override
    public PostResponse getById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Post not Found")
        );

        return PostMapper.toPostResponse(post);
    }

    @Override
    public Post findById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found with id " + id));
    }

    @Override
    @Transactional
    public Post updateById(Long id, PostRequest request) {
        Post existingPost = findById(id);

        District newDistrict = districtService.getById(request.getDistrictId());

        existingPost.setDistrict(newDistrict);
        existingPost.setTitle(request.getTitle());
        existingPost.setDescription(request.getDescription());
        existingPost.setBudgetMin(request.getBudgetMin());
        existingPost.setBudgetMax(request.getBudgetMax());
        existingPost.setFinishDay(request.getFinishDay());
        existingPost.setPostStatus(PostStatus.AVAILABLE);
        existingPost.setImageUrl(request.getImageUrl());

        return postRepository.save(existingPost);
    }

    @Override
    public void deleteById(Long id) {
        postRepository.delete(findById(id));
    }
}
