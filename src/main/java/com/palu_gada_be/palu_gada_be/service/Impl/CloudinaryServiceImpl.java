package com.palu_gada_be.palu_gada_be.service.Impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.palu_gada_be.palu_gada_be.dto.response.CloudinaryResponse;
import com.palu_gada_be.palu_gada_be.service.CloudinaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CloudinaryServiceImpl implements CloudinaryService {

    private final Cloudinary cloudinary;

    @Override
    public CloudinaryResponse uploadFile(MultipartFile file) throws IOException {
        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
        return CloudinaryResponse.builder()
                .url(uploadResult.get("secure_url").toString())
                .publicId(uploadResult.get("public_id").toString())
                .build();
    }

    @Override
    public Map getFileByPublicId(String publicId) {
        try {
            return cloudinary.api().resource(publicId, ObjectUtils.emptyMap());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to get file from Cloudinary", e);
        }
    }

    @Override
    public CloudinaryResponse replaceFile(String publicId, MultipartFile file) throws IOException {
        Map uploadResult = cloudinary.uploader().upload(file.getBytes(),
                ObjectUtils.asMap("public_id", publicId, "overwrite", true));

        return CloudinaryResponse.builder()
                .url(uploadResult.get("secure_url").toString())
                .publicId(uploadResult.get("public_id").toString())
                .build();
    }

    @Override
    public Map deleteFileByPublicId(String publicId) {
        try {
            return cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to delete file from Cloudinary", e);
        }
    }
}
