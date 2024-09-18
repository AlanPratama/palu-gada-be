package com.palu_gada_be.palu_gada_be.specification;

import com.palu_gada_be.palu_gada_be.model.UserReport;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;


public class UserReportSpecification {
    public static Specification<UserReport> messageLike(String messageLike) {
        return (root, query, builder) -> builder.like(root.get("message"), "%" + messageLike + "%");
    }

    public static Specification<UserReport> inUserReported(List<Long> userIds) {
        return (root, query, builder) -> root.get("userReported").get("id").in(userIds);
    }

    public static Specification<UserReport> inUserReport(List<Long> userIds) {
        return (root, query, builder) -> root.get("user").get("id").in(userIds);
    }

    public static Specification<UserReport> sortByField(String sortField, String sortDirection) {
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
