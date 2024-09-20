package com.palu_gada_be.palu_gada_be.specification;

import com.palu_gada_be.palu_gada_be.constant.PostStatus;
import com.palu_gada_be.palu_gada_be.model.Post;
import com.palu_gada_be.palu_gada_be.model.PostCategory;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;


public class PostSpecification {
    public static Specification<Post> byUserId(Long userId) {
        return (root, query, builder) -> builder.equal(root.get("user").get("id"), userId);
    }

    public static Specification<Post> titleLike(String titleLike) {
        return (root, query, builder) -> builder.like(root.get("title"), "%" + titleLike + "%");
    }

    public static Specification<Post> inDistrict(List<Long> districtIds) {
        return (root, query, builder) -> root.get("district").get("id").in(districtIds);
    }

    public static Specification<Post> byCategoryIds(List<Long> categoryIds) {
        return (root, query, builder) -> {
            Join<Post, PostCategory> postCategoryJoin = root.join("postCategories");
            return postCategoryJoin.get("category").get("id").in(categoryIds);
        };
    }

    public static Specification<Post> byPostStatus(PostStatus status) {
        return (root, query, builder) -> builder.equal(root.get("postStatus"), status);
    }

    public static Specification<Post> sortByField(String sortField, String sortDirection) {
        return (root, query, builder) -> {
            if ("asc".equalsIgnoreCase(sortDirection)) {
                query.orderBy(builder.asc(root.get(sortField)));
            } else {
                query.orderBy(builder.desc(root.get(sortField)));
            }
            return null;
        };
    }
}
