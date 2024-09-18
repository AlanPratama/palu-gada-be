package com.palu_gada_be.palu_gada_be.specification;

import com.palu_gada_be.palu_gada_be.model.Bid;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;


public class BidSpecification {
    // First request parameter filter: Get Bids with name like a specific string
    public static Specification<Bid> messageLike(String messageLike) {
        return (root, query, builder) -> builder.like(root.get("message"), "%" + messageLike + "%");
    }

    // First request parameter filter: Get Bids with name like a specific string
    public static Specification<Bid> statusLike(String statusLike) {
        return (root, query, builder) -> builder.like(root.get("status"), "%" + statusLike + "%");
    }

    public static Specification<Bid> sortByField(String sortField, String sortDirection) {
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
