package com.palu_gada_be.palu_gada_be.dto.request;

import lombok.*;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewRequest {

    @NotNull(message = "Post ID tidak boleh kosong")
    private Long postId;

    @NotNull(message = "Rating tidak boleh kosong")
    @Min(value = 1, message = "Rating minimal adalah 1")
    @Max(value = 5, message = "Rating maksimal adalah 5")
    private Long rating;

    @NotBlank(message = "Komentar tidak boleh kosong")
    @Size(max = 255, message = "Komentar tidak boleh lebih dari 255 karakter")
    private String comment;
}

