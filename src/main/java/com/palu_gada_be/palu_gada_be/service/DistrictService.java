package com.palu_gada_be.palu_gada_be.service;

import com.palu_gada_be.palu_gada_be.dto.request.DistrictRequest;
import com.palu_gada_be.palu_gada_be.model.District;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DistrictService {
    District create(DistrictRequest request);
    Page<District> getAll(Pageable pageable);
    District getById(Long id);
    District update(Long id, DistrictRequest request);
    void deleteById(Long id);
}
