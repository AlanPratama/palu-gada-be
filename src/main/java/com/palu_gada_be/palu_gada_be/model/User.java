package com.palu_gada_be.palu_gada_be.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.palu_gada_be.palu_gada_be.constant.ConstantTable;
import com.palu_gada_be.palu_gada_be.constant.UserGender;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = ConstantTable.USER)
@EntityListeners(AuditingEntityListener.class)
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "districtId")
    private District district;

    @Column(name = "name", nullable = true)
    private String name;

    @Column(name = "phone", nullable = true)
    private String phone;

    @Column(name = "email", nullable = true)
    private String email;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    @JsonIgnore
    private String password;

    @Column(name = "address")
    private String address;

    @Column(name = "balance", nullable = false)
    @Min(value = 0, message = "Balance must be at least 0")
    private Long balance;

    @Column(name = "photoUrl")
    private String photoUrl;

    @Column(name = "birthDate")
    private String birthDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = true)
    private UserGender userGender;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Payment> payments = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Bid> bids = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<UserCategory> userCategories = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<UserReport> userReports;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<PostReport> postReports;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Review> reviews = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    @CreatedDate
    @Column(name = "createdAt", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updatedAt")
    private LocalDateTime updatedAt;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toSet());
    }

    @PrePersist
    public void prePersist() {
        if (this.balance == null) {
            this.balance = 0L;
        }
    }
}
