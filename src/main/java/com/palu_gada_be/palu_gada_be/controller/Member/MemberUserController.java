package com.palu_gada_be.palu_gada_be.controller.Member;

import com.palu_gada_be.palu_gada_be.constant.ConstantEndpoint;
import com.palu_gada_be.palu_gada_be.dto.request.ResetPasswordRequest;
import com.palu_gada_be.palu_gada_be.dto.request.UserUpdateRequest;
import com.palu_gada_be.palu_gada_be.service.UserService;
import com.palu_gada_be.palu_gada_be.util.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(ConstantEndpoint.MEMBER_USER_API)
@RequiredArgsConstructor
@Tag(name = "Users", description = "APIs for managing authenticated user's profile")
public class MemberUserController {

    private final UserService userService;

    @Operation(summary = "Get authenticated user data", description = "Retrieve the authenticated user's profile information.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved user data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized access")
    })
    @GetMapping
    public ResponseEntity<?> getAuthenticated() {
        return Response.renderJSON(
                userService.getAuthentication(),
                "Success Get Authenticated User Data",
                HttpStatus.OK
        );
    }

    @Operation(summary = "Update authenticated user profile", description = "Update the authenticated user's profile information, including optional profile picture upload.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated user profile"),
            @ApiResponse(responseCode = "400", description = "Invalid request data")
    })
    @PutMapping
    public ResponseEntity<?> updateUser(
            @Valid @ModelAttribute UserUpdateRequest user,
            @RequestParam(value = "file", required = false) MultipartFile file
    ) {
        return Response.renderJSON(
                userService.updateById(user, file),
                "Success Update User Profile",
                HttpStatus.OK
        );
    }

    @Operation(summary = "Reset user password", description = "Reset the password of the authenticated user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully reset password"),
            @ApiResponse(responseCode = "400", description = "Invalid reset password request")
    })
    @PutMapping("/reset-password")
    public ResponseEntity<?> resetPassword(
            @Valid @RequestBody ResetPasswordRequest request
    ) {
        return Response.renderJSON(
                userService.resetPassword(request),
                "Success Reset Password",
                HttpStatus.OK
        );
    }
}
