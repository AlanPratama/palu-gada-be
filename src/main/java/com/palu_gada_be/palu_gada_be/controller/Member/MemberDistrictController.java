package com.palu_gada_be.palu_gada_be.controller.Member;

import com.palu_gada_be.palu_gada_be.constant.ConstantEndpoint;
import com.palu_gada_be.palu_gada_be.model.District;
import com.palu_gada_be.palu_gada_be.service.DistrictService;
import com.palu_gada_be.palu_gada_be.util.PageResponse;
import com.palu_gada_be.palu_gada_be.util.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ConstantEndpoint.MEMBER_DISTRICT_API)
@RequiredArgsConstructor
@Tag(name = "Districts", description = "APIs for managing districts")
public class MemberDistrictController {

    private final DistrictService districtService;

    @Operation(summary = "Get all districts", description = "Retrieve a paginated list of districts, with optional filters for name and sorting.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved districts", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PageResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters", content = @Content)
    })
    @GetMapping
    public ResponseEntity<?> getAll(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String sortField,
            @RequestParam(required = false) String sortDirection,
            @PageableDefault Pageable pageable
    ) {
        return Response.renderJSON(
                new PageResponse<>(districtService.getAll(name, sortField, sortDirection, pageable)),
                "Success Get All District",
                HttpStatus.OK
        );
    }

    @Operation(summary = "Get district by ID", description = "Retrieve a district by its unique ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved district", content = @Content(mediaType = "application/json", schema = @Schema(implementation = District.class))),
            @ApiResponse(responseCode = "404", description = "District not found", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(
            @PathVariable Long id
    ) {
        return Response.renderJSON(
                districtService.getById(id),
                "Success Get District",
                HttpStatus.OK
        );
    }
}
