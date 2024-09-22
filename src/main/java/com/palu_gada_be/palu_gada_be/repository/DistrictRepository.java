package com.palu_gada_be.palu_gada_be.repository;

import com.palu_gada_be.palu_gada_be.model.District;
import com.palu_gada_be.palu_gada_be.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DistrictRepository extends JpaRepository<District, Long>, JpaSpecificationExecutor<District> {
}
