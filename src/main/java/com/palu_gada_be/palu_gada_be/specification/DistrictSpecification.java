package com.palu_gada_be.palu_gada_be.specification;

import com.palu_gada_be.palu_gada_be.model.District;
import org.springframework.data.jpa.domain.Specification;

public class DistrictSpecification {
    public static Specification<District> nameLike(String nameLike) {
        return (root, query, builder) -> builder.like(root.get("districtName"), "%" + nameLike + "%");
    }

    public static Specification<District> sortByField(String sortField, String sortDirection) {
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
