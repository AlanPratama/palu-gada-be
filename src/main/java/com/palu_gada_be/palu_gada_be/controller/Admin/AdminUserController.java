package com.palu_gada_be.palu_gada_be.controller.Admin;

import com.palu_gada_be.palu_gada_be.constant.ConstantEndpoint;
import com.palu_gada_be.palu_gada_be.dto.request.RegisterRequest;
import com.palu_gada_be.palu_gada_be.service.UserService;
import com.palu_gada_be.palu_gada_be.util.PageResponse;
import com.palu_gada_be.palu_gada_be.util.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ConstantEndpoint.ADMIN_USER_API)
@RequiredArgsConstructor
public class AdminUserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<?> getAll(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) List<Long> districtIds,
            @RequestParam(required = false) String sortField,
            @RequestParam(required = false) String sortDirection,
            @PageableDefault Pageable pageable
    ) {
        System.out.println(districtIds);
        return Response.renderJSON(new PageResponse<>(userService.getAll(name, districtIds, sortField, sortDirection, pageable)), "Success Get Data", HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createAdmin(
            @RequestBody RegisterRequest request
    ) {
        return Response.renderJSON(
                userService.createAdmin(request),
                "Success Create Admin",
                HttpStatus.CREATED
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(
            @PathVariable Long id
    ) {
        return Response.renderJSON(
                userService.getById(id),
                "Success Get Data User Id: " + id,
                HttpStatus.OK
        );
    }

    @GetMapping("/me")
    public ResponseEntity<?> getUserAuthenticated() {
        return Response.renderJSON(
                userService.getAuthentication(),
                "Success Get Data User Authenticated",
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(
            @PathVariable Long id
    ) {
        userService.deleteById(id);
        return Response.renderJSON(
                id,
                "Success Delete User"
        );
    }



}
