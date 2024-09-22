package com.palu_gada_be.palu_gada_be.service.Impl;

import com.palu_gada_be.palu_gada_be.model.PendingBid;
import com.palu_gada_be.palu_gada_be.repository.PendingBidRepository;
import com.palu_gada_be.palu_gada_be.service.PendingBidService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PendingBidServiceImpl implements PendingBidService {

    private final PendingBidRepository pendingBidRepository;

    @Override
    public PendingBid create(PendingBid request) {
        return pendingBidRepository.save(request);
    }

    @Override
    public PendingBid getById(Long id) {
        return pendingBidRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Pending Bid Not Found")
        );
    }

    @Override
    public void delete(Long id) {
        pendingBidRepository.delete(getById(id));
    }
}
