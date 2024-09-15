package com.palu_gada_be.palu_gada_be.service.Impl;

import com.palu_gada_be.palu_gada_be.constant.ConstantEndpoint;
import com.palu_gada_be.palu_gada_be.dto.request.MidtransRequest;
import com.palu_gada_be.palu_gada_be.dto.response.MidtransResponse;
import com.palu_gada_be.palu_gada_be.service.MidtransService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class MidtransServiceImpl implements MidtransService {

    @Value("${midtrans.server.key}")
    private String SERVER_KEY;
    private final RestTemplate restTemplate;

    @Override
    public HttpHeaders getServerHeader() {
        HttpHeaders httpHeaders = new HttpHeaders();
        String auth = SERVER_KEY + ":";
        byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(StandardCharsets.US_ASCII));
        String authHeader = "Basic " + new String(encodedAuth);

        httpHeaders.set("Authorization", authHeader);
        httpHeaders.set("Content-Type", "application/json");

        return httpHeaders;
    }

    @Override
    public MidtransResponse chargeTransaction(MidtransRequest midtransRequest) {
        HttpEntity<MidtransRequest> request = new HttpEntity<>(midtransRequest, getServerHeader());

        ResponseEntity<MidtransResponse> response = restTemplate.exchange(
                ConstantEndpoint.MIDTRANS_ENDPOINT + "/charge",
                HttpMethod.POST,
                request,
                MidtransResponse.class
        );

        return response.getBody();
    }

    @Override
    public MidtransResponse fetchTransaction(String id) {
        HttpEntity<?> request = new HttpEntity<>(null, getServerHeader());

        ResponseEntity<MidtransResponse> response = restTemplate.exchange(
                ConstantEndpoint.MIDTRANS_ENDPOINT + "/" + id + "/status",
                HttpMethod.GET,
                request,
                MidtransResponse.class
        );

        return response.getBody();
    }

    @Override
    public MidtransResponse updateTransactionStatus(String id, String status) {
        HttpEntity<?> request = new HttpEntity<>(null, getServerHeader());

        ResponseEntity<MidtransResponse> response = restTemplate.exchange(
                ConstantEndpoint.MIDTRANS_ENDPOINT + "/" + id + "/" + status,
                HttpMethod.POST,
                request,
                MidtransResponse.class
        );

        return response.getBody();
    }
}
