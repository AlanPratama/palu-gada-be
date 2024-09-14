package com.palu_gada_be.palu_gada_be.service;

import com.palu_gada_be.palu_gada_be.dto.request.PostRequest;
import com.palu_gada_be.palu_gada_be.dto.response.PostResponse;
import com.palu_gada_be.palu_gada_be.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostService {
    PostResponse create(PostRequest request);
    Page<PostResponse> getAll(Pageable pageable);
    Page<PostResponse> getAllByUserId(Pageable pageable);
    PostResponse getById(Long id);
    Post findById(Long id);
    Post updateById(Long id, PostRequest request);
    void deleteById(Long id);
}
