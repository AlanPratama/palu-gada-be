package com.palu_gada_be.palu_gada_be.service;

import com.palu_gada_be.palu_gada_be.model.PendingBid;

public interface PendingBidService {
    PendingBid create(PendingBid request);
    PendingBid getById(Long id);
    void delete(Long id);
}
