package com.palu_gada_be.palu_gada_be.configuration;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class CloudinaryConfig {

    @Value("${cloudinary.api.name}")
    private String CLOUD_NAME;

    @Value("${cloudinary.api.key}")
    private String API_KEY;

    @Value("${cloudinary.api.secret}")
    private String API_SECRET;

    @Bean
    public Cloudinary cloudinary() {
        Map<String, String> config = ObjectUtils.asMap(
                "cloud_name", CLOUD_NAME,
                "api_key", API_KEY,
                "api_secret", API_SECRET
        );

        return new Cloudinary(config);
    }

}
