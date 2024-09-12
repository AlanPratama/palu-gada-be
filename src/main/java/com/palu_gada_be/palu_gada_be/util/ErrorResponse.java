package com.palu_gada_be.palu_gada_be.util;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@Builder
public class ErrorResponse<T> {
    private String statusCode;
    private String statusText;
    private String message;
    private List<T> errors;
}
