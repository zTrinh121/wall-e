package com.example.SWP391_Project.config;

import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfig {

    @Bean
    public Cloudinary cloudinary() {
        final Map<String, String> config = new HashMap<>();
        config.put("cloud_name", "dtga7c02b");
        config.put("api_key", "428282437337617");
        config.put("api_secret", "giAVkdh4DcacF1uKkl5UIz_QrIw");
        return new Cloudinary(config);
    }

}
