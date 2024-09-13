package com.palu_gada_be.palu_gada_be.service.Impl;

import com.palu_gada_be.palu_gada_be.dto.request.post.PostRequest;
import com.palu_gada_be.palu_gada_be.model.Post;
import com.palu_gada_be.palu_gada_be.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class PostServiceImpl implements PostService {
    @Override
    public Post create(PostRequest request) {
        return null;
    }

    @Override
    public Page<Post> getAll(Pageable pageable) {
        return null;
    }

    @Override
    public Post getById(Long id) {
        return null;
    }

    @Override
    public Post update(Long id, PostRequest request) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
