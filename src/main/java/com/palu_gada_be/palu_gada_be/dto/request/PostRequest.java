package com.palu_gada_be.palu_gada_be.dto.request;

import lombok.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;


import java.util.List;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostRequest {

    @NotNull(message = "District ID tidak boleh kosong")
    private Long districtId;

    @NotBlank(message = "Judul tidak boleh kosong")
    @Size(max = 100, message = "Judul tidak boleh lebih dari 100 karakter")
    private String title;

    @NotBlank(message = "Deskripsi tidak boleh kosong")
    @Size(max = 1000, message = "Deskripsi tidak boleh lebih dari 1000 karakter")
    private String description;

    @NotNull(message = "Budget minimum tidak boleh kosong")
    @Min(value = 1, message = "Budget minimum harus lebih besar atau sama dengan 1")
    private Long budgetMin;

    @NotNull(message = "Budget maksimum tidak boleh kosong")
    @Min(value = 1, message = "Budget maksimum harus lebih besar atau sama dengan 1")
    private Long budgetMax;

    @NotBlank(message = "Deadline tidak boleh kosong")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "Deadline harus dalam format YYYY-MM-DD")
    private String deadline;

    private List<Long> categoriesId;

    @NotNull(message = "Hari selesai tidak boleh kosong")
    @Min(value = 1, message = "Hari selesai harus lebih besar atau sama dengan 1")
    private Long finishDay;

    @NotNull(message = "Status urgensi tidak boleh kosong")
    private Boolean isUrgent;
}

