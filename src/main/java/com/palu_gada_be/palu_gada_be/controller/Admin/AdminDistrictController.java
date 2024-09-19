package com.palu_gada_be.palu_gada_be.controller.Admin;

import com.palu_gada_be.palu_gada_be.constant.ConstantEndpoint;
import com.palu_gada_be.palu_gada_be.dto.request.DistrictRequest;
import com.palu_gada_be.palu_gada_be.service.DistrictService;
import com.palu_gada_be.palu_gada_be.util.PageResponse;
import com.palu_gada_be.palu_gada_be.util.Response;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ConstantEndpoint.ADMIN_DISTRICT_API)
@RequiredArgsConstructor
public class AdminDistrictController {

    private final DistrictService districtService;

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

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
        @PathVariable Long id,
        @Valid @RequestBody DistrictRequest request
    ) {
        return Response.renderJSON(
            districtService.update(id, request),
            "Success Update District",
            HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(
        @PathVariable Long id
    ) {
        districtService.deleteById(id);
        return Response.renderJSON(
                id,
                "Success Delete District"
        );
    }
}
