package com.palu_gada_be.palu_gada_be.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DistrictRequest {

    @NotNull(message = "Nama kecamatan tidak boleh kosong.")
    @Size(min = 3, max = 100, message = "Nama kecamatan harus memiliki antara 3 hingga 100 karakter.")
    private String name;

    @NotNull(message = "Nama kabupaten/kota tidak boleh kosong.")
    @Size(min = 3, max = 100, message = "Nama kabupaten/kota harus memiliki antara 3 hingga 100 karakter.")
    private String regency;

    @NotNull(message = "Nama provinsi tidak boleh kosong.")
    @Size(min = 3, max = 100, message = "Nama provinsi harus memiliki antara 3 hingga 100 karakter.")
    private String province;
}

