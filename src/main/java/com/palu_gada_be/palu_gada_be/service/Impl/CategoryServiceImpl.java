package com.palu_gada_be.palu_gada_be.service.Impl;

import com.palu_gada_be.palu_gada_be.dto.request.category.CategoryRequest;
import com.palu_gada_be.palu_gada_be.model.Category;
import com.palu_gada_be.palu_gada_be.repository.CategoryRepository;
import com.palu_gada_be.palu_gada_be.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public Category create(CategoryRequest request) {
        Category newCategory = Category.builder()
                .name(request.getName())
                .build();

        return categoryRepository.save(newCategory);
    }

    @Override
    public Page<Category> getAll(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    @Override
    public Category getById(Long id) {
        return categoryRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Category Not Found")
        );
    }

    @Override
    public Category update(Long id, CategoryRequest request) {
        Category exitingCategory = getById(id);
        exitingCategory.setName(request.getName());
        return categoryRepository.save(exitingCategory);
    }

    @Override
    public void deleteById(Long id) {
        categoryRepository.delete(getById(id));
    }
}
