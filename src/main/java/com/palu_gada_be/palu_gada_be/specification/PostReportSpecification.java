package com.palu_gada_be.palu_gada_be.specification;

import com.palu_gada_be.palu_gada_be.model.PostReport;
import org.springframework.data.jpa.domain.Specification;

public class PostReportSpecification {
    public static Specification<PostReport> messageLike(String messageLike) {
        return (root, query, builder) -> builder.like(root.get("message"), "%" + messageLike + "%");
    }

    public static Specification<PostReport> sortByField(String sortField, String sortDirection) {
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
