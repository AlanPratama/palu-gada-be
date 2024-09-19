package com.palu_gada_be.palu_gada_be.service.Impl;

import com.palu_gada_be.palu_gada_be.constant.PostStatus;
import com.palu_gada_be.palu_gada_be.dto.request.PostRequest;
import com.palu_gada_be.palu_gada_be.dto.response.CloudinaryResponse;
import com.palu_gada_be.palu_gada_be.dto.response.PostResponse;
import com.palu_gada_be.palu_gada_be.mapper.PostMapper;
import com.palu_gada_be.palu_gada_be.model.District;
import com.palu_gada_be.palu_gada_be.model.Post;
import com.palu_gada_be.palu_gada_be.model.PostCategory;
import com.palu_gada_be.palu_gada_be.model.User;
import com.palu_gada_be.palu_gada_be.repository.PostRepository;
import com.palu_gada_be.palu_gada_be.security.JwtService;
import com.palu_gada_be.palu_gada_be.service.*;
import com.palu_gada_be.palu_gada_be.util.DateTimeUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static com.palu_gada_be.palu_gada_be.specification.PostSpecification.*;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final DistrictService districtService;
    private final JwtService jwtService;
    private final PostCategoryService postCategoryService;
    private final CategoryService categoryService;
    private final CloudinaryService cloudinaryService;

    @Override
    @Transactional
    public PostResponse create(PostRequest request, MultipartFile file) {
        User user = jwtService.getUserAuthenticated();
        District district = districtService.getById(request.getDistrictId());

        LocalDateTime deadline;
        if (request.getDeadline() != null && !request.getDeadline().isEmpty()) {
            deadline = DateTimeUtil.convertStringToLocalDateTime(request.getDeadline(), "yyyy-MM-dd HH:mm:ss");
        } else {
            deadline = LocalDateTime.now().plus(30, ChronoUnit.DAYS);
        }

        if (request.getBudgetMin() > request.getBudgetMax()) {
            throw new RuntimeException("Budget must be valid");
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
                .isUrgent(request.getIsUrgent())
                .build();

        if (file != null && !file.isEmpty()){
            try {
                CloudinaryResponse response = cloudinaryService.uploadFile(file);
                newPost.setImageUrl(response.getUrl());
            } catch (IOException e){
                throw new RuntimeException("Failed to upload image", e);
            }
        }

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
            throw new RuntimeException("Categories not found");
        }

        newPost.setPostCategories(postCategories);
        postRepository.save(newPost);

        return PostMapper.toPostResponse(newPost);
    }

    @Override
    public Page<PostResponse> getAll(String titleLikeFilter, List<Long> districtIds, String sortField, String sortDirection, Pageable pageable) {
        Specification<Post> spec = Specification.where(StringUtils.isBlank(titleLikeFilter) ? null : titleLike(titleLikeFilter))
                .and(CollectionUtils.isEmpty(districtIds) ? null : inDistrict(districtIds))
                .and(StringUtils.isBlank(sortField) ? null : sortByField(sortField, sortDirection));

        Page<Post> posts = postRepository.findAll(spec, pageable);

        return posts.map(PostMapper::toPostResponse);
    }

    @Override
    public Page<PostResponse> getAllByUserId(String titleLikeFilter, String sortField, String sortDirection, Pageable pageable) {
        Long id = jwtService.getUserAuthenticated().getId();

        Specification<Post> spec = Specification.where(byUserId(id))
                .and(StringUtils.isBlank(titleLikeFilter) ? null : titleLike(titleLikeFilter))
                .and(StringUtils.isBlank(sortField) ? null : sortByField(sortField, sortDirection));

        Page<Post> posts = postRepository.findAll(spec, pageable);

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
    public PostResponse updateStatusPost(Long id, String status) {
        Post post = findById(id);
        try {
            post.setPostStatus(PostStatus.valueOf(status.toUpperCase()));
            postRepository.save(post);
        } catch (IllegalArgumentException e){
            throw new IllegalArgumentException("Invalid status value");
        }
        return PostMapper.toPostResponse(post);
    }

    @Override
    public Post findById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found with id " + id));
    }

    @Override
    @Transactional
    public Post updateById(Long id, PostRequest request, MultipartFile file) {
        Post existingPost = findById(id);

        District newDistrict = districtService.getById(request.getDistrictId());

        existingPost.setDistrict(newDistrict);
        existingPost.setTitle(request.getTitle());
        existingPost.setDescription(request.getDescription());
        existingPost.setBudgetMin(request.getBudgetMin());
        existingPost.setBudgetMax(request.getBudgetMax());
        existingPost.setFinishDay(request.getFinishDay());
        existingPost.setPostStatus(PostStatus.AVAILABLE);

        if (file != null && !file.isEmpty()){
            try {
                CloudinaryResponse response = cloudinaryService.uploadFile(file);
                existingPost.setImageUrl(response.getUrl());
            } catch (IOException e){
                throw new RuntimeException("Failed to upload image", e);
            }
        }

        return postRepository.save(existingPost);
    }

    @Override
    public void deleteById(Long id) {
        postRepository.delete(findById(id));
    }
}
