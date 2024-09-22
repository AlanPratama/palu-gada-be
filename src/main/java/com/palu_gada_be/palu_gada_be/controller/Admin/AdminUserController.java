package com.palu_gada_be.palu_gada_be.controller.Admin;

import com.palu_gada_be.palu_gada_be.constant.ConstantEndpoint;
import com.palu_gada_be.palu_gada_be.dto.request.RegisterRequest;
import com.palu_gada_be.palu_gada_be.dto.request.ResetPasswordRequest;
import com.palu_gada_be.palu_gada_be.dto.request.UserUpdateRequest;
import com.palu_gada_be.palu_gada_be.dto.response.UserResponse;
import com.palu_gada_be.palu_gada_be.service.UserService;
import com.palu_gada_be.palu_gada_be.util.PageResponse;
import com.palu_gada_be.palu_gada_be.util.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(ConstantEndpoint.ADMIN_USER_API)
@RequiredArgsConstructor
@Tag(name = "Admin User", description = "APIs for managing users by admin")
public class AdminUserController {

    private final UserService userService;

    @Operation(summary = "Get all users", description = "Retrieve all users with optional filters and pagination.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved all users", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PageResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping
    public ResponseEntity<?> getAll(
            @Parameter(description = "Filter by name") @RequestParam(required = false) String name,
            @Parameter(description = "Filter by district IDs") @RequestParam(required = false) List<Long> districtIds,
            @Parameter(description = "Field to sort by") @RequestParam(required = false) String sortField,
            @Parameter(description = "Sort direction, either asc or desc") @RequestParam(required = false) String sortDirection,
            @PageableDefault Pageable pageable
    ) {
        return Response.renderJSON(
                new PageResponse<>(userService.getAll(name, districtIds, sortField, sortDirection, pageable)),
                "Success Get Data",
                HttpStatus.OK
        );
    }

    @Operation(summary = "Create new admin", description = "Create a new admin user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created admin", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad request, validation failed", content = @Content)
    })
    @PostMapping
    public ResponseEntity<?> createAdmin(
            @Valid @RequestBody RegisterRequest request
    ) {
        return Response.renderJSON(
                userService.createAdmin(request),
                "Success Create Admin",
                HttpStatus.CREATED
        );
    }

    @Operation(summary = "Get user by ID", description = "Retrieve a specific user by their ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved user", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(
            @Parameter(description = "ID of the user") @PathVariable Long id
    ) {
        return Response.renderJSON(
                userService.getById(id),
                "Success Get Data User Id: " + id,
                HttpStatus.OK
        );
    }

    @Operation(summary = "Update user", description = "Update user information and optionally upload a profile picture.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated user", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad request, validation failed", content = @Content)
    })
    @PutMapping
    public ResponseEntity<?> updateUser(
            @Valid @ModelAttribute UserUpdateRequest user,
            @RequestParam(value = "file", required = false) MultipartFile file
    ) {
        return Response.renderJSON(
                userService.updateById(user, file),
                "Success Update User",
                HttpStatus.OK
        );
    }

    @Operation(summary = "Reset user password", description = "Reset the password of a user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully reset password", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad request, validation failed", content = @Content)
    })
    @PutMapping("/update-password")
    public ResponseEntity<?> resetPassword(
            @Valid @RequestBody ResetPasswordRequest request
    ) {
        return Response.renderJSON(
                userService.resetPassword(request),
                "Success Reset Password",
                HttpStatus.OK
        );
    }

    @Operation(summary = "Get authenticated user", description = "Retrieve the authenticated user's information.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved authenticated user", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized, user not authenticated", content = @Content)
    })
    @GetMapping("/me")
    public ResponseEntity<?> getUserAuthenticated() {
        return Response.renderJSON(
                userService.getAuthentication(),
                "Success Get Data User Authenticated",
                HttpStatus.OK
        );
    }

    @Operation(summary = "Delete user by ID", description = "Delete a specific user by their ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted user", content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(
            @Parameter(description = "ID of the user") @PathVariable Long id
    ) {
        userService.deleteById(id);
        return Response.renderJSON(
                id,
                "Success Delete User"
        );
    }
}
