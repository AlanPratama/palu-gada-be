package com.palu_gada_be.palu_gada_be.service;

import com.palu_gada_be.palu_gada_be.dto.request.MidtransRequest;
import com.palu_gada_be.palu_gada_be.dto.response.MidtransResponse;
import org.springframework.http.HttpHeaders;

public interface MidtransService {
    HttpHeaders getServerHeader();
    MidtransResponse chargeTransaction(MidtransRequest midtransRequest);
    MidtransResponse fetchTransaction(String id);
    MidtransResponse updateTransactionStatus(String id, String status);
}
