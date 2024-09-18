package com.palu_gada_be.palu_gada_be.repository;

import com.palu_gada_be.palu_gada_be.model.District;
import com.palu_gada_be.palu_gada_be.model.Post;
import com.palu_gada_be.palu_gada_be.model.PostReport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostReportRepository extends JpaRepository<PostReport, Long>, JpaSpecificationExecutor<PostReport> {
    List<PostReport> findByUserId(Long id);
    Page<PostReport> findByUserId(Long id, Pageable pageable);
    List<PostReport> findByPostId(Long id);
    Page<PostReport> findByPostId(Long id, Pageable pageable);
}
