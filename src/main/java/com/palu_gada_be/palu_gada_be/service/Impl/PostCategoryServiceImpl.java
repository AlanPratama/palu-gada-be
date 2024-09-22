package com.palu_gada_be.palu_gada_be.service.Impl;

import com.palu_gada_be.palu_gada_be.dto.request.PostCategoryRequest;
import com.palu_gada_be.palu_gada_be.model.PostCategory;
import com.palu_gada_be.palu_gada_be.repository.PostCategoryRepository;
import com.palu_gada_be.palu_gada_be.service.CategoryService;
import com.palu_gada_be.palu_gada_be.service.PostCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostCategoryServiceImpl implements PostCategoryService {

    private final PostCategoryRepository postCategoryRepository;
    private final CategoryService categoryService;

    @Override
    public PostCategory create(PostCategory request) {
        return postCategoryRepository.save(request);
    }

    @Override
    public List<PostCategory> createAll(List<PostCategory> request) {
        return postCategoryRepository.saveAll(request);
    }
}
