package com.palu_gada_be.palu_gada_be.controller.Admin;

import com.palu_gada_be.palu_gada_be.constant.ConstantEndpoint;
import com.palu_gada_be.palu_gada_be.dto.request.CategoryRequest;
import com.palu_gada_be.palu_gada_be.model.Category;
import com.palu_gada_be.palu_gada_be.service.CategoryService;
import com.palu_gada_be.palu_gada_be.util.PageResponse;
import com.palu_gada_be.palu_gada_be.util.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ConstantEndpoint.ADMIN_CATEGORY_API)
@RequiredArgsConstructor
@Tag(name = "Admin Category", description = "APIs for managing categories by admin")
public class AdminCategoryController {

    private final CategoryService categoryService;

    @Operation(summary = "Get all categories", description = "Retrieve a list of categories with optional filters.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PageResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping
    public ResponseEntity<?> getAll(
            @Parameter(description = "Filter by category name") @RequestParam(required = false) String name,
            @Parameter(description = "Field to sort by") @RequestParam(required = false) String sortField,
            @Parameter(description = "Sort direction (asc/desc)") @RequestParam(required = false) String sortDirection,
            @PageableDefault Pageable pageable
    ) {
        return Response.renderJSON(
                new PageResponse<>(categoryService.getAll(name, sortField, sortDirection, pageable)),
                "Success Get Categories",
                HttpStatus.OK
        );
    }

    @Operation(summary = "Create a new category", description = "Create a new category by providing the necessary details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Category successfully created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Category.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content)
    })
    @PostMapping
    public ResponseEntity<?> create(
            @Valid @RequestBody CategoryRequest request
    ) {
        return Response.renderJSON(
                categoryService.create(request),
                "Success Created Category",
                HttpStatus.CREATED
        );
    }

    @Operation(summary = "Get category by ID", description = "Retrieve a category by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category successfully retrieved", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Category.class))),
            @ApiResponse(responseCode = "404", description = "Category not found", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(
            @Parameter(description = "ID of the category to be retrieved") @PathVariable Long id
    ) {
        return Response.renderJSON(
                categoryService.getById(id),
                "Success Get Category",
                HttpStatus.OK
        );
    }

    @Operation(summary = "Update a category", description = "Update the details of an existing category.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category successfully updated", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Category.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
            @ApiResponse(responseCode = "404", description = "Category not found", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @Parameter(description = "ID of the category to be updated") @PathVariable Long id,
            @Valid @RequestBody CategoryRequest request
    ) {
        return Response.renderJSON(
                categoryService.update(id, request),
                "Success Update Category",
                HttpStatus.OK
        );
    }

    @Operation(summary = "Delete a category", description = "Delete a category by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category successfully deleted", content = @Content),
            @ApiResponse(responseCode = "404", description = "Category not found", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(
            @Parameter(description = "ID of the category to be deleted") @PathVariable Long id
    ) {
        categoryService.deleteById(id);
        return Response.renderJSON(
                id,
                "Success Delete Category"
        );
    }
}
