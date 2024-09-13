package com.palu_gada_be.palu_gada_be.service.Impl;

import com.palu_gada_be.palu_gada_be.dto.request.DistrictRequest;
import com.palu_gada_be.palu_gada_be.model.District;
import com.palu_gada_be.palu_gada_be.repository.DistrictRepository;
import com.palu_gada_be.palu_gada_be.service.DistrictService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DistrictServiceImpl implements DistrictService {

    private final DistrictRepository districtRepository;

    @Override
    public District create(DistrictRequest request) {

        District newDistrict = District.builder()
                .districtName(request.getName())
                .province(request.getProvince())
                .regency(request.getRegency())
                .build();

        return districtRepository.save(newDistrict);
    }

    @Override
    public Page<District> getAll(Pageable pageable) {
        return districtRepository.findAll(pageable);
    }

    @Override
    public District getById(Long id) {
        return districtRepository.findById(id).orElseThrow(
                () -> new RuntimeException("District Not Found")
        );
    }

    @Override
    public District update(Long id, DistrictRequest request) {

        District existingDistrict = getById(id);

        existingDistrict.setDistrictName(request.getName());
        existingDistrict.setProvince(request.getProvince());
        existingDistrict.setRegency(request.getRegency());

        return districtRepository.save(existingDistrict);
    }

    @Override
    public void deleteById(Long id) {
        districtRepository.delete(getById(id));
    }
}
