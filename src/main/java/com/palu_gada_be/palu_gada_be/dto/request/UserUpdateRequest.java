package com.palu_gada_be.palu_gada_be.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateRequest {

    private Long districtId;

    @Pattern(regexp = "^\\+?[0-9]{7,15}$", message = "Nomor Hp tidak valid")
    private String phone;

    @Size(max = 255, message = "Alamat tidak boleh lebih dari 255 karakter")
    private String address;

    @NotBlank(message = "Nama tidak boleh kosong")
    @Size(max = 100, message = "Nama tidak boleh lebih dari 100 karakter")
    private String name;

    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "Tanggal lahir harus format YYYY-MM-DD")
    private String birthDate;

    @Pattern(regexp = "^(Male|Female|other)$", message = "Jenis Kelamin harus Male dan Female")
    private String userGender;

    private List<Long> userCategoriesId;
}
