package com.palu_gada_be.palu_gada_be.specification;

import com.palu_gada_be.palu_gada_be.model.Post;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;


public class PostSpecification {
    public static Specification<Post> byUserId(Long userId) {
        return (root, query, builder) -> builder.equal(root.get("user").get("id"), userId);
    }

    // First request parameter filter: Get Users with title like a specific string
    public static Specification<Post> titleLike(String titleLike) {
        return (root, query, builder) -> builder.like(root.get("title"), "%" + titleLike + "%");
    }

    // Third request parameter filter: Get Posts in one of the specified district
    public static Specification<Post> inDistrict(List<Long> districtIds) {
        return (root, query, builder) -> root.get("district").get("id").in(districtIds);
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
