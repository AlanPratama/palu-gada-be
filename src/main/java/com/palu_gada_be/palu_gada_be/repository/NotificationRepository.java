package com.palu_gada_be.palu_gada_be.repository;

import com.palu_gada_be.palu_gada_be.model.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUserId(Long id);
    Page<Notification> findByUserId(Long id, Pageable pageable);
}
