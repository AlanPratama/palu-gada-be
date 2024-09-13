package com.palu_gada_be.palu_gada_be.service;

import com.palu_gada_be.palu_gada_be.dto.request.PostCategoryRequest;
import com.palu_gada_be.palu_gada_be.model.PostCategory;

import java.util.List;

public interface PostCategoryService {
    PostCategory create(PostCategory request);
    List<PostCategory> createAll(List<PostCategory> request);
}
