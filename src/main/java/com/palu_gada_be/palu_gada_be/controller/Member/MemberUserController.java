package com.palu_gada_be.palu_gada_be.controller.Member;

import com.palu_gada_be.palu_gada_be.constant.ConstantEndpoint;
import com.palu_gada_be.palu_gada_be.dto.request.ChangePasswordRequest;
import com.palu_gada_be.palu_gada_be.dto.request.ResetPasswordRequest;
import com.palu_gada_be.palu_gada_be.dto.request.UserUpdateRequest;
import com.palu_gada_be.palu_gada_be.service.MemberService;
import com.palu_gada_be.palu_gada_be.service.UserService;
import com.palu_gada_be.palu_gada_be.util.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(ConstantEndpoint.MEMBER_USER_API)
@RequiredArgsConstructor
public class MemberUserController {

    private final MemberService memberService;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<?> getAuthenticated() {
        return Response.renderJSON(
                memberService.getAuthentication(),
                "Success Get Data Member",
                HttpStatus.OK
        );
    }

    @PutMapping
    public ResponseEntity<?> updateUser(
        @ModelAttribute UserUpdateRequest user,
        @RequestParam(value = "file", required = false) MultipartFile file
    ) {
        return Response.renderJSON(
            userService.updateById(user, file),
            "Success Update User",
                HttpStatus.OK
        );
    }

    @PutMapping("/update-password")
    public ResponseEntity<?> resetPassword(
            @RequestBody ResetPasswordRequest request
    ) {
        return Response.renderJSON(
                memberService.resetPassword(request),
                "Success Reset Password",
                HttpStatus.OK
        );
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(
            @RequestBody ChangePasswordRequest request
    ) {
        return Response.renderJSON(
                memberService.changePassword(request),
                "Success Change Password",
                HttpStatus.OK
        );
    }
}
