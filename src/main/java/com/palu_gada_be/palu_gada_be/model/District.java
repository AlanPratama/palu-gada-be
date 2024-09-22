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
@Table(name = ConstantTable.DISTRICT)
@EntityListeners(AuditingEntityListener.class)
public class District {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "districtName", nullable = false)
    private String districtName;

    @Column(name = "regency", nullable = false)
    private String regency;

    @Column(name = "provice", nullable = false)
    private String province;

    @OneToMany(mappedBy = "district")
    @JsonIgnore
    private List<User> users = new ArrayList<>();

    @OneToMany(mappedBy = "district")
    @JsonIgnore
    private List<Post> posts = new ArrayList<>();

    @CreatedDate
    @Column(name = "createdAt", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updatedAt")
    private LocalDateTime updatedAt;
}
