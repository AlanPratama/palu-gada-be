package com.palu_gada_be.palu_gada_be.controller.Member;

import com.palu_gada_be.palu_gada_be.constant.ConstantEndpoint;
import com.palu_gada_be.palu_gada_be.dto.request.user.ResetPasswordRequest;
import com.palu_gada_be.palu_gada_be.service.MemberService;
import com.palu_gada_be.palu_gada_be.util.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ConstantEndpoint.MEMBER_USER_API)
@RequiredArgsConstructor
public class MemberUserController {

    private final MemberService memberService;

    @GetMapping
    public ResponseEntity<?> getAuthenticated() {
        return Response.renderJSON(
                memberService.getAuthentication(),
                "Success Get Data Member",
                HttpStatus.OK
        );
    }

    @PostMapping
    public ResponseEntity<?> resetPassword(
            @RequestBody ResetPasswordRequest request
    ) {
        return Response.renderJSON(
                memberService.resetPassword(request),
                "Success Reset Password",
                HttpStatus.OK
        );
    }
}
