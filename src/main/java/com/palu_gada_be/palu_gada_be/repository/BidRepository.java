package com.palu_gada_be.palu_gada_be.repository;

import com.palu_gada_be.palu_gada_be.model.Bid;
import com.palu_gada_be.palu_gada_be.model.District;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BidRepository extends JpaRepository<Bid, Long>, JpaSpecificationExecutor<Bid> {
    List<Bid> findByUserId(Long id);
    Page<Bid> findByUserId(Long id, Pageable pageable);
    List<Bid> findByPostId(Long id);
    Page<Bid> findByPostId(Long id, Pageable pageable);

    @Query("SELECT COUNT(b) FROM Bid b WHERE b.user.id = :userId AND b.bidStatus IN ('ACCEPTED', 'FINISH', 'REVIEWED')")
    Long countManyUserWorking(@Param("userId") Long userId);
}
