package com.palu_gada_be.palu_gada_be.service.Impl;

import com.palu_gada_be.palu_gada_be.model.UserCategory;
import com.palu_gada_be.palu_gada_be.repository.UserCategoryRepository;
import com.palu_gada_be.palu_gada_be.service.UserCategoyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserCategoryServiceImpl implements UserCategoyService {

    private final UserCategoryRepository userCategoryRepository;

    @Override
    public UserCategory create(UserCategory request) {
        return userCategoryRepository.save(request);
    }

    @Override
    public List<UserCategory> createAll(List<UserCategory> request) {
        return userCategoryRepository.saveAll(request);
    }
}
