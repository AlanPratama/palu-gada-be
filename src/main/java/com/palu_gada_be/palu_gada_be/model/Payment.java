package com.palu_gada_be.palu_gada_be.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.palu_gada_be.palu_gada_be.constant.ConstantTable;
import com.palu_gada_be.palu_gada_be.constant.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = ConstantTable.PAYMENT)
@EntityListeners(AuditingEntityListener.class)
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "userId")
    private User user;

    @Column(name = "trfDate", nullable = false)
    private LocalDateTime transferDate;

    @Column(name = "bank", nullable = false)
    private String bank;

    @Column(name = "amount", nullable = false)
    private Long amount;

    @Column(name = "vaNumber", nullable = false)
    private String vaNumber;

    @Column(name = "paymentType", nullable = false)
    private String paymentType;

    @Column(name = "expiryTime", nullable = false)
    private String expiryTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private PaymentStatus paymentStatus;

    @CreatedDate
    @Column(name = "createdAt", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updatedAt")
    private LocalDateTime updatedAt;
}
