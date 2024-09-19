package com.palu_gada_be.palu_gada_be.repository;

import com.palu_gada_be.palu_gada_be.model.Payout;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PayoutRepository extends JpaRepository<Payout, Long> {
    List<Payout> findByUserId(Long id);
    Page<Payout> findByUserId(Long id, Pageable pageable);
}
