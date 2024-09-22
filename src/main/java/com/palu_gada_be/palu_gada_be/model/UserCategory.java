package com.palu_gada_be.palu_gada_be.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.palu_gada_be.palu_gada_be.constant.ConstantTable;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = ConstantTable.USER_CATEGORY)
@EntityListeners(AuditingEntityListener.class)
public class UserCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "userId")
    @JsonIgnore
    private User user;

    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Category category;

    @CreatedDate
    @Column(name = "createdAt", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updatedAt")
    private LocalDateTime updatedAt;
}
