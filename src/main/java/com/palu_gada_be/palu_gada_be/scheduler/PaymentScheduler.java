package com.palu_gada_be.palu_gada_be.scheduler;

import com.palu_gada_be.palu_gada_be.model.Payment;
import com.palu_gada_be.palu_gada_be.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PaymentScheduler {

    private final PaymentService paymentService;

    @Scheduled(fixedRate = 300000)
    public void checkPaymentStatus() {

        List<Payment> pendingPayments = paymentService.getPendingPayments();

        pendingPayments.forEach(payment -> {
            try {
                paymentService.fetchTransaction(payment.getId());
            } catch (Exception e) {
                throw new RuntimeException("Scheduler Broken");
            }
        });


    }

}
