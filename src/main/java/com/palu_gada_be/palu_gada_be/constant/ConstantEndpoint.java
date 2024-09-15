package com.palu_gada_be.palu_gada_be.constant;

public class ConstantEndpoint {
    public static final String AUTH_API = "/api/v1/auth";

    /* MEMBER ENDPOINT */
    public static final String MEMBER_USER_API = "/api/v1/users";
    public static final String MEMBER_POST_API = "/api/v1/posts";
    public static final String MEMBER_BID_API = "/api/v1/bids";
    public static final String MEMBER_PAYMENT_API = "/api/v1/payments";

    /* ADMIN ENDPOINT */
    public static final String ADMIN_USER_API = "/api/v1/admin/users";
    public static final String ADMIN_CATEGORY_API = "/api/v1/admin/categories";
    public static final String ADMIN_DISTRICT_API = "/api/v1/admin/districts";
    public static final String ADMIN_POST_API = "/api/v1/admin/posts";
    public static final String ADMIN_BID_API = "/api/v1/admin/bids";
    public static final String ADMIN_PAYMENT_API = "/api/v1/admin/payments";

    /* MIDTRANS ENDPOINT */
    public static final String MIDTRANS_ENDPOINT = "https://api.sandbox.midtrans.com/v2";
}
