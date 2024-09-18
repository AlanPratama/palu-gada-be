package com.palu_gada_be.palu_gada_be.specification;

import com.palu_gada_be.palu_gada_be.model.Payment;
import org.springframework.data.jpa.domain.Specification;


public class PaymentSpecification {
    // First request parameter filter: Get Payments with name like a specific string
    public static Specification<Payment> bankLike(String bankLike) {
        return (root, query, builder) -> builder.like(root.get("bank"), "%" + bankLike + "%");
    }

    // First request parameter filter: Get Payments with name like a specific string
    public static Specification<Payment> statusLike(String statusLike) {
        return (root, query, builder) -> builder.like(root.get("paymentStatus"), "%" + statusLike + "%");
    }

    public static Specification<Payment> sortByField(String sortField, String sortDirection) {
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
