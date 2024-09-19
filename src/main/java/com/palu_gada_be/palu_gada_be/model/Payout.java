package com.palu_gada_be.palu_gada_be.model;

import com.palu_gada_be.palu_gada_be.constant.ConstantTable;
import com.palu_gada_be.palu_gada_be.constant.PayoutStatus;
import com.palu_gada_be.palu_gada_be.constant.PayoutType;
import com.palu_gada_be.palu_gada_be.constant.PostStatus;
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
@Table(name = ConstantTable.PAYOUT)
@EntityListeners(AuditingEntityListener.class)
public class Payout {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @Column(name = "amount", nullable = false)
    private Long amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "payoutType", nullable = false)
    private PayoutType payoutType;

    @Column(name = "destinationNumber", nullable = false)
    private String destinationNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private PayoutStatus payoutStatus;

    @CreatedDate
    @Column(name = "createdAt", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updatedAt")
    private LocalDateTime updatedAt;


}
