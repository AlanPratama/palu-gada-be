package com.palu_gada_be.palu_gada_be.controller;

import com.palu_gada_be.palu_gada_be.constant.ConstantEndpoint;
import com.palu_gada_be.palu_gada_be.dto.response.CloudinaryResponse;
import com.palu_gada_be.palu_gada_be.service.CloudinaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping(ConstantEndpoint.FILE_API)
@RequiredArgsConstructor
public class FileController {

    private final CloudinaryService cloudinaryService;

    @PostMapping("/upload")
    public ResponseEntity<CloudinaryResponse> uploadFile(
        @RequestParam("file") MultipartFile file
    ) {
        try {
            CloudinaryResponse response = cloudinaryService.uploadFile(file);
            return ResponseEntity.ok(response);
        } catch (IOException e){
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }


    @GetMapping("/{publicId}")
    public ResponseEntity<Map> getFile(@PathVariable String publicId) throws Exception {
        try {
            Map fileData = cloudinaryService.getFileByPublicId(publicId);
            return ResponseEntity.ok(fileData);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }
}
