package com.palu_gada_be.palu_gada_be.repository;

import com.palu_gada_be.palu_gada_be.model.Post;
import com.palu_gada_be.palu_gada_be.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>, JpaSpecificationExecutor<Post> {
    List<Post> findByUserId(Long id);
    Page<Post> findByUserId(Long id, Pageable pageable);
}
