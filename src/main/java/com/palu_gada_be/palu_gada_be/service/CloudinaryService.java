package com.palu_gada_be.palu_gada_be.service;

import com.palu_gada_be.palu_gada_be.dto.response.CloudinaryResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

public interface CloudinaryService {
    CloudinaryResponse uploadFile(MultipartFile file) throws IOException;
    Map getFileByPublicId(String publicId);
    Map deleteFileByPublicId(String publicId);
}
