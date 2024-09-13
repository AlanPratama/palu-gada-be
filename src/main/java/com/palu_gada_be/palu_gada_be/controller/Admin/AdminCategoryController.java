package com.palu_gada_be.palu_gada_be.controller.Admin;

import com.palu_gada_be.palu_gada_be.constant.ConstantEndpoint;
import com.palu_gada_be.palu_gada_be.dto.request.CategoryRequest;
import com.palu_gada_be.palu_gada_be.service.CategoryService;
import com.palu_gada_be.palu_gada_be.util.PageResponse;
import com.palu_gada_be.palu_gada_be.util.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ConstantEndpoint.ADMIN_CATEGORY_API)
@RequiredArgsConstructor
public class AdminCategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<?> getAll(
        @PageableDefault Pageable pageable
    ) {
        return Response.renderJSON(
                new PageResponse<>(categoryService.getAll(pageable)),
                "Success Get Categories",
                HttpStatus.OK
        );
    }

    @PostMapping
    public ResponseEntity<?> create(
        @RequestBody CategoryRequest request
    ) {
       return Response.renderJSON(
               categoryService.create(request),
               "Success Created Category",
               HttpStatus.CREATED
       );
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(
        @PathVariable Long id
    ) {
        return Response.renderJSON(
            categoryService.getById(id),
            "Success Get Category",
            HttpStatus.OK
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
        @PathVariable Long id,
        @RequestBody CategoryRequest request
    ) {
        return Response.renderJSON(
            categoryService.update(id, request),
            "Success Update Category",
            HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(
            @PathVariable Long id
    ) {
        categoryService.deleteById(id);
        return Response.renderJSON(
                id,
                "Success Delete Category"
        );
    }
}
