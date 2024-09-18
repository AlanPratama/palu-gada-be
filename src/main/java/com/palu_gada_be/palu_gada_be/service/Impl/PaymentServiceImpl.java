package com.palu_gada_be.palu_gada_be.service.Impl;

import com.palu_gada_be.palu_gada_be.constant.ConstantPayment;
import com.palu_gada_be.palu_gada_be.constant.PaymentStatus;
import com.palu_gada_be.palu_gada_be.dto.request.*;
import com.palu_gada_be.palu_gada_be.dto.response.MidtransResponse;
import com.palu_gada_be.palu_gada_be.dto.response.PaymentResponse;
import com.palu_gada_be.palu_gada_be.mapper.PaymentMapper;
import com.palu_gada_be.palu_gada_be.model.Payment;
import com.palu_gada_be.palu_gada_be.model.User;
import com.palu_gada_be.palu_gada_be.repository.PaymentRepository;
import com.palu_gada_be.palu_gada_be.security.JwtService;
import com.palu_gada_be.palu_gada_be.service.MidtransService;
import com.palu_gada_be.palu_gada_be.service.PaymentService;
import com.palu_gada_be.palu_gada_be.service.UserService;
import com.palu_gada_be.palu_gada_be.util.DateTimeUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final MidtransService midtransService;
    private final PaymentRepository paymentRepository;
    private final JwtService jwtService;
    private final UserService userService;

    @Override
    public Page<PaymentResponse> getAll(Pageable pageable) {
        Page<Payment> payments = paymentRepository.findAll(pageable);

        return payments.map(PaymentMapper::toPaymentResponse);
    }

    @Override
    public Page<PaymentResponse> getByUserId(Long id, Pageable pageable) {
        Page<Payment> payments = paymentRepository.findByUserId(id, pageable);

        return payments.map(PaymentMapper::toPaymentResponse);
    }

    @Override
    public Page<PaymentResponse> getByUserAuthenticated(Pageable pageable) {
        Long id = jwtService.getUserAuthenticated().getId();
        Page<Payment> payments = paymentRepository.findByUserId(id, pageable);

        return payments.map(PaymentMapper::toPaymentResponse);
    }

    @Override
    public Payment findById(Long id) {
        return paymentRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Payment not found")
        );
    }

    @Override
    public List<Payment> getPendingPayments() {
        return paymentRepository.findByPaymentStatus(PaymentStatus.PENDING);
    }

    @Override
    public PaymentResponse getById(Long id) {
        Payment payment = findById(id);
        return PaymentMapper.toPaymentResponse(payment);
    }

    @Override
    @Transactional
    public PaymentResponse create(PaymentRequest request) throws Exception {
        User authenticated = jwtService.getUserAuthenticated();

        Payment payment = Payment.builder()
                .bank(request.getBank())
                .user(authenticated)
                .transferDate(LocalDateTime.now())
                .fee(ConstantPayment.FEE)
                .amount(request.getAmount())
                .paymentType(request.getPaymentType())
                .paymentStatus(PaymentStatus.PENDING)
                .vaNumber("Not Yet Created")
                .expiryTime(DateTimeUtil.convertLocalDateTimeToString(
                        LocalDateTime.now().plusMinutes(Long.parseLong(ConstantPayment.EXPIRY_DURATION)),
                        "yyyy-MM-dd HH:mm:ss"
                ))
                .build();

        Payment createdPayment = paymentRepository.save(payment);

        try {
            MidtransRequest midtransRequest = MidtransRequest.builder()
                    .payment_type(createdPayment.getPaymentType())
                    .transaction_details(TransactionDetailRequest.builder()
                            .gross_amount(createdPayment.getAmount())
                            .order_id(ConstantPayment.ORDER_ID_PREFIX + createdPayment.getId())
                            .build())
                    .bank_transfer(BankTransferRequest.builder()
                            .bank(createdPayment.getBank())
                            .build())
                    .custom_expiry(CustomExpiryRequest.builder()
                            .expiry_duration(ConstantPayment.EXPIRY_DURATION)
                            .unit(ConstantPayment.UNIT)
                            .build())
                    .build();

            MidtransResponse midtransResponse = midtransService.chargeTransaction(midtransRequest);
            createdPayment.setVaNumber(midtransResponse.getVaNumbers().get(0).getVaNumber());
            createdPayment.setPaymentStatus(PaymentStatus.valueOf(midtransResponse.getTransactionStatus().toUpperCase()));
            createdPayment.setExpiryTime(midtransResponse.getExpiryTime());
            createdPayment.setTransferDate(
                    DateTimeUtil.convertStringToLocalDateTime(midtransResponse.getTransactionTime(), "yyyy-MM-dd HH:mm:ss")
            );

        } catch (Exception e){
            throw new Exception(e.getMessage());
        }

        Payment fetchedPayment = paymentRepository.save(createdPayment);

        return PaymentMapper.toPaymentResponse(fetchedPayment);
    }

    @Override
    @Transactional
    public PaymentResponse fetchTransaction(Long id) throws Exception {
        MidtransResponse midtransResponse = midtransService.fetchTransaction(ConstantPayment.ORDER_ID_PREFIX + id);
        Payment payment = findById(id);
        payment.setPaymentStatus(PaymentStatus.valueOf(midtransResponse.getTransactionStatus().toUpperCase()));
        paymentRepository.save(payment);
        try {
            if (payment.getPaymentStatus().equals(PaymentStatus.SETTLEMENT)){
                userService.updateBalance(payment.getUser().getId(), payment.getAmount());
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

        return PaymentMapper.toPaymentResponse(payment);
    }

    @Override
    @Transactional
    public PaymentResponse updateTransaction(Long id, String status) throws Exception {
        MidtransResponse midtransResponse = midtransService.updateTransactionStatus(ConstantPayment.ORDER_ID_PREFIX + id, status.toLowerCase());
        Payment payment = findById(id);
        payment.setPaymentStatus(PaymentStatus.valueOf(midtransResponse.getTransactionStatus().toUpperCase()));
        paymentRepository.save(payment);

        try {
            if (payment.getPaymentStatus().equals(PaymentStatus.SETTLEMENT)){
                userService.updateBalance(payment.getUser().getId(), payment.getAmount());
            }
        } catch (Exception e) {
            throw new Exception("Someting Wrong when update transaction, please try again later");
        }

        return PaymentMapper.toPaymentResponse(payment);
    }
}
