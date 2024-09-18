package com.palu_gada_be.palu_gada_be.service;

import com.palu_gada_be.palu_gada_be.dto.request.CategoryRequest;
import com.palu_gada_be.palu_gada_be.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {
    Category create(CategoryRequest request);
    Page<Category> getAll(String nameLikeFilter, String sortField, String sortDirection, Pageable pageable);
    Category getById(Long id);
    Category update(Long id, CategoryRequest request);
    void deleteById(Long id);
}
