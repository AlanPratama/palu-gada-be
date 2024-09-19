package com.palu_gada_be.palu_gada_be.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostCategoryRequest {

    @NotNull(message = "ID postingan tidak boleh kosong.")
    private Long postId;

    @NotNull(message = "ID kategori tidak boleh kosong.")
    private Long categoryId;
}

