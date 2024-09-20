package com.palu_gada_be.palu_gada_be.controller.Admin;

import com.palu_gada_be.palu_gada_be.constant.ConstantEndpoint;
import com.palu_gada_be.palu_gada_be.dto.request.DistrictRequest;
import com.palu_gada_be.palu_gada_be.model.District;
import com.palu_gada_be.palu_gada_be.service.DistrictService;
import com.palu_gada_be.palu_gada_be.util.PageResponse;
import com.palu_gada_be.palu_gada_be.util.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequestMapping(ConstantEndpoint.ADMIN_DISTRICT_API)
@RequiredArgsConstructor
@Tag(name = "Admin District", description = "APIs for managing districts by admin")
public class AdminDistrictController {

    private final DistrictService districtService;

    @Operation(summary = "Get all districts", description = "Retrieve a list of districts with optional filters.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PageResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping
    public ResponseEntity<?> getAll(
            @Parameter(description = "Filter by district name") @RequestParam(required = false) String name,
            @Parameter(description = "Field to sort by") @RequestParam(required = false) String sortField,
            @Parameter(description = "Sort direction (asc/desc)") @RequestParam(required = false) String sortDirection,
            @PageableDefault Pageable pageable
    ) {
        return Response.renderJSON(
                new PageResponse<>(districtService.getAll(name, sortField, sortDirection, pageable)),
                "Success Get All District",
                HttpStatus.OK
        );
    }

    @Operation(summary = "Create a new district", description = "Create a new district by providing the necessary details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "District successfully created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = District.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content)
    })
    @PostMapping
    public ResponseEntity<?> create(
            @Valid @RequestBody DistrictRequest request
    ) {
        return Response.renderJSON(
                districtService.create(request),
                "Success Create District",
                HttpStatus.CREATED
        );
    }

    @Operation(summary = "Get district by ID", description = "Retrieve a district by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "District successfully retrieved", content = @Content(mediaType = "application/json", schema = @Schema(implementation = District.class))),
            @ApiResponse(responseCode = "404", description = "District not found", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(
            @Parameter(description = "ID of the district to be retrieved") @PathVariable Long id
    ) {
        return Response.renderJSON(
                districtService.getById(id),
                "Success Get District",
                HttpStatus.OK
        );
    }

    @Operation(summary = "Update a district", description = "Update the details of an existing district.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "District successfully updated", content = @Content(mediaType = "application/json", schema = @Schema(implementation = District.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
            @ApiResponse(responseCode = "404", description = "District not found", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @Parameter(description = "ID of the district to be updated") @PathVariable Long id,
            @Valid @RequestBody DistrictRequest request
    ) {
        return Response.renderJSON(
                districtService.update(id, request),
                "Success Update District",
                HttpStatus.OK
        );
    }

    @Operation(summary = "Delete a district", description = "Delete a district by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "District successfully deleted", content = @Content),
            @ApiResponse(responseCode = "404", description = "District not found", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(
            @Parameter(description = "ID of the district to be deleted") @PathVariable Long id
    ) {
        districtService.deleteById(id);
        return Response.renderJSON(
                id,
                "Success Delete District"
        );
    }
}
