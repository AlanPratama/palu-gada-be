package com.palu_gada_be.palu_gada_be.controller;

import com.palu_gada_be.palu_gada_be.constant.ConstantEndpoint;
import com.palu_gada_be.palu_gada_be.dto.request.MidtransRequest;
import com.palu_gada_be.palu_gada_be.service.MidtransService;
import com.palu_gada_be.palu_gada_be.util.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/midtrans")
@RequiredArgsConstructor
public class MidtransController {

    private final MidtransService midtransService;

    @PostMapping("/charge")
    public ResponseEntity<?> charge(
        @RequestBody MidtransRequest request
    ) {
        return Response.renderJSON(
                midtransService.chargeTransaction(request),
                "Success Charge Transaction"
        );
    }

}
