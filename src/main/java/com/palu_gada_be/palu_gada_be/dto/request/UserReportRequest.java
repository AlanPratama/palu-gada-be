package com.palu_gada_be.palu_gada_be.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserReportRequest {

    @NotNull(message = "User tidak boleh kosong")
    private Long userId;

    @NotBlank(message = "Message tidak boleh kosong")
    private String message;
}
