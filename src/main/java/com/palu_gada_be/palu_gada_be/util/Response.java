package com.palu_gada_be.palu_gada_be.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class Response {
    public static <T> ResponseEntity<?> renderJSON(T data, String message, HttpStatus httpStatus){
        WebResponse<T> response = WebResponse.<T>builder()
                .message(message)
                .status(httpStatus.getReasonPhrase())
                .data(data)
                .build();
        return ResponseEntity.status(httpStatus).body(response);
    }

    public static <T> ResponseEntity<?> renderJSON(T data, String message){
        return renderJSON(data, message, HttpStatus.OK);
    }
}
