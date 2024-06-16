package com.example.SWP391_Project.service.impl;

import com.cloudinary.Cloudinary;
import com.example.SWP391_Project.exception.FuncErrorException;
import com.example.SWP391_Project.response.CloudinaryResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Service
public class CloudinaryService {

    @Autowired
    private Cloudinary cloudinary;

    @Transactional
    public CloudinaryResponse uploadFile(final MultipartFile file,final String fileName) {
        try {
            final Map result = cloudinary.uploader()
                                         .upload(file.getBytes(),
                                                 Map.of("public_id",
                                                         "Wall-E/" + fileName));
            final String url      = (String) result.get("secure_url");
            final String publicId = (String) result.get("public_id");
            return CloudinaryResponse.builder().publicId(publicId).url(url).build();
        } catch (final Exception e) {
            throw new FuncErrorException("Failed to upload file");
        }
    }


}
