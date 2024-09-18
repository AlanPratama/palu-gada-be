package com.palu_gada_be.palu_gada_be.specification;

import com.palu_gada_be.palu_gada_be.model.Review;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class ReviewSpecification {
    public static Specification<Review> commentLike(String commentLike) {
        return (root, query, builder) -> builder.like(root.get("comment"), "%" + commentLike + "%");
    }

    public static Specification<Review> inRating(List<Long> rating) {
        return (root, query, builder) -> root.get("rating").in(rating);
    }

    public static Specification<Review> sortByField(String sortField, String sortDirection) {
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
