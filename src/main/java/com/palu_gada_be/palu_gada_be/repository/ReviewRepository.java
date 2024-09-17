package com.palu_gada_be.palu_gada_be.repository;

import com.palu_gada_be.palu_gada_be.model.Bid;
import com.palu_gada_be.palu_gada_be.model.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByUserId(Long id);
    Page<Review> findByUserId(Long id, Pageable pageable);
    List<Review> findByPostId(Long id);
    Page<Review> findByPostId(Long id, Pageable pageable);
}
