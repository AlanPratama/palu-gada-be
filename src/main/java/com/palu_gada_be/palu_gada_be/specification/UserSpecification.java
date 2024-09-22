package com.palu_gada_be.palu_gada_be.specification;

import java.util.List;

import com.palu_gada_be.palu_gada_be.model.User;
import org.springframework.data.jpa.domain.Specification;


public class UserSpecification {
    // First request parameter filter: Get Users with name like a specific string
    public static Specification<User> nameLike(String nameLike) {
        return (root, query, builder) -> builder.like(root.get("name"), "%" + nameLike + "%");
    }

    // Third request parameter filter: Get Users in one of the specified district
    public static Specification<User> inDistrict(List<Long> districtIds) {
        return (root, query, builder) -> root.get("district").get("id").in(districtIds);
    }

    public static Specification<User> sortByField(String sortField, String sortDirection) {
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
