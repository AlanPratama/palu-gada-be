package com.palu_gada_be.palu_gada_be.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryRequest {

    @NotNull(message = "Nama kategori tidak boleh kosong.")
    @Size(min = 3, max = 50, message = "Nama kategori harus memiliki antara 3 hingga 50 karakter.")
    private String name;
}

