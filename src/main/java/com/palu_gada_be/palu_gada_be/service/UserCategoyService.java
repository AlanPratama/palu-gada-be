package com.palu_gada_be.palu_gada_be.service;

import com.palu_gada_be.palu_gada_be.model.UserCategory;

import java.util.List;

public interface UserCategoyService {
    UserCategory create(UserCategory request);
    List<UserCategory> createAll(List<UserCategory> request);
}
