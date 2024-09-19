package com.palu_gada_be.palu_gada_be.controller;

import com.palu_gada_be.palu_gada_be.constant.ConstantEndpoint;
import com.palu_gada_be.palu_gada_be.dto.request.AuthenticationRequest;
import com.palu_gada_be.palu_gada_be.dto.request.RefreshTokenRequest;
import com.palu_gada_be.palu_gada_be.dto.request.RegisterRequest;
import com.palu_gada_be.palu_gada_be.dto.response.AuthenticationResponse;
import com.palu_gada_be.palu_gada_be.dto.response.RefreshTokenResponse;
import com.palu_gada_be.palu_gada_be.dto.response.UserRegisterResponse;
import com.palu_gada_be.palu_gada_be.service.AuthService;
import com.palu_gada_be.palu_gada_be.service.UserService;
import com.palu_gada_be.palu_gada_be.util.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ConstantEndpoint.AUTH_API)
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Authentication management APIs")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    @Operation(summary = "User login", description = "Authentikasi user dan mengembalikan token JWT.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login berhasil", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthenticationResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content)
    })
    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody @Valid AuthenticationRequest request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }

    @Operation(summary = "User registration", description = "Mendaftarkan user baru.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Registrasi berhasil", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserRegisterResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content)
    })
    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestBody @Valid RegisterRequest request
    ) {
        return ResponseEntity.ok(authService.register(request));
    }

    @Operation(summary = "Create admin user", description = "Membuat user dengan role admin. Header 'X-Admin-Secret-Key' diperlukan untuk otorisasi.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Admin berhasil dibuat", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserRegisterResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden, invalid secret key", content = @Content)
    })
    @PostMapping("/create-admin")
    public ResponseEntity<?> createAdmin(
            @RequestBody @Valid RegisterRequest request,
            @RequestHeader(value = "X-Admin-Secret-Key") @Parameter(description = "Admin secret key for authorization") String adminSecretKey
    ) {
        return Response.renderJSON(userService.createAdmin(request), "Admin Successfully Created", HttpStatus.CREATED);
    }

    @Operation(summary = "Refresh token", description = "Mengambil token baru dengan token refresh.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Token berhasil diperbarui", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RefreshTokenResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
    })
    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest refreshToken) throws Exception {
        return ResponseEntity.ok(authService.refreshToken(refreshToken));
    }
}
