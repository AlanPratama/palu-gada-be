package com.palu_gada_be.palu_gada_be.repository;

import com.palu_gada_be.palu_gada_be.model.Post;
import com.palu_gada_be.palu_gada_be.model.User;
import com.palu_gada_be.palu_gada_be.model.UserReport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface UserReportRepository extends JpaRepository<UserReport, Long>, JpaSpecificationExecutor<UserReport> {
    List<UserReport> findByUserId(Long id);
    Page<UserReport> findByUserId(Long id, Pageable pageable);
}
