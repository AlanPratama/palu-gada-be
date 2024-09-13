package com.palu_gada_be.palu_gada_be.service;

import com.palu_gada_be.palu_gada_be.dto.request.PostRequest;
import com.palu_gada_be.palu_gada_be.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostService {
    Post create(PostRequest request);
    Page<Post> getAll(Pageable pageable);
    Page<Post> getAllByUserId(Pageable pageable);
    Post getById(Long id);
    Post updateById(Long id, PostRequest request);
    void deleteById(Long id);
}
