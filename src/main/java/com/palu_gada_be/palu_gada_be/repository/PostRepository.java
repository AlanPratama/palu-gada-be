package com.palu_gada_be.palu_gada_be.repository;

import com.palu_gada_be.palu_gada_be.dto.request.post.PostRequest;
import com.palu_gada_be.palu_gada_be.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

}
