package com.palu_gada_be.palu_gada_be.service.Impl;

import com.palu_gada_be.palu_gada_be.constant.PayoutStatus;
import com.palu_gada_be.palu_gada_be.constant.PayoutType;
import com.palu_gada_be.palu_gada_be.dto.request.PayoutRequest;
import com.palu_gada_be.palu_gada_be.dto.response.PayoutResponse;
import com.palu_gada_be.palu_gada_be.mapper.PayoutMapper;
import com.palu_gada_be.palu_gada_be.model.Payout;
import com.palu_gada_be.palu_gada_be.model.User;
import com.palu_gada_be.palu_gada_be.repository.PayoutRepository;
import com.palu_gada_be.palu_gada_be.repository.UserRepository;
import com.palu_gada_be.palu_gada_be.security.JwtService;
import com.palu_gada_be.palu_gada_be.service.PayoutService;
import com.palu_gada_be.palu_gada_be.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PayoutServiceImpl implements PayoutService {

    private final PayoutRepository payoutRepository;
    private final UserService userService;
    private final JwtService jwtService;

    @Override
    public PayoutResponse create(PayoutRequest request) {

        User user = jwtService.getUserAuthenticated();

        if (user.getBalance() < request.getAmount()) {
            throw new IllegalArgumentException("Balance tidak mencukupi untuk melakukan payout.");
        }

        userService.updateBalance(user.getId(), Math.abs(user.getBalance() - request.getAmount()) * -1);

        Payout payout = Payout.builder()
                .user(user)
                .amount(request.getAmount())
                .payoutType(PayoutType.valueOf(request.getPayoutType().toUpperCase()))
                .destinationNumber(request.getDestinationNumber())
                .payoutStatus(PayoutStatus.PENDING)
                .build();

        payoutRepository.save(payout);

        return PayoutMapper.toPaymentResponse(payout);
    }

    @Override
    public Page<PayoutResponse> getAll(Pageable pageable) {
        Page<Payout> payouts = payoutRepository.findAll(pageable);
        return payouts.map(PayoutMapper::toPaymentResponse);
    }

    @Override
    public Page<PayoutResponse> getAllByUserId(Long id, Pageable pageable) {
        Page<Payout> payouts = payoutRepository.findByUserId(id, pageable);
        return payouts.map(PayoutMapper::toPaymentResponse);
    }

    @Override
    public Page<PayoutResponse> getAllUserAuthenticated(Pageable pageable) {
        User user = jwtService.getUserAuthenticated();
        Page<Payout> payouts = payoutRepository.findByUserId(user.getId(), pageable);
        return payouts.map(PayoutMapper::toPaymentResponse);
    }

    @Override
    public Payout findById(Long id) {
        return payoutRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Payout not found"));
    }

    @Override
    public PayoutResponse getById(Long id) {
        Payout payout = findById(id);
        return PayoutMapper.toPaymentResponse(payout);
    }

    @Override
    public PayoutResponse update(Long id, String status) {
        Payout payout = findById(id);

        try {
            payout.setPayoutStatus(PayoutStatus.valueOf(status.toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid status value", e);
        }

        if (payout.getPayoutStatus().equals(PayoutStatus.FAILED)){
            userService.updateBalance(payout.getUser().getId(), payout.getAmount());
        }

        payoutRepository.save(payout);
        return PayoutMapper.toPaymentResponse(payout);
    }
}
