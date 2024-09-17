package com.palu_gada_be.palu_gada_be.service;

import com.palu_gada_be.palu_gada_be.dto.request.PostRequest;
import com.palu_gada_be.palu_gada_be.dto.response.PostResponse;
import com.palu_gada_be.palu_gada_be.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface PostService {
    PostResponse create(PostRequest request, MultipartFile file);
    Page<PostResponse> getAll(Pageable pageable);
    Page<PostResponse> getAllByUserId(Pageable pageable);
    PostResponse getById(Long id);
    PostResponse updateStatusPost(Long id, String status);
    Post findById(Long id);
    Post updateById(Long id, PostRequest request, MultipartFile file);
    void deleteById(Long id);
}
