package com.example.SWP391_Project.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.SWP391_Project.exception.FuncErrorException;
import com.example.SWP391_Project.response.CloudinaryResponse;
import com.example.SWP391_Project.utils.FileUploadUtil;
import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryService {

    @Autowired
    private Cloudinary cloudinary;

    @Transactional
    public CloudinaryResponse uploadImageFile(final MultipartFile file,final String fileName) {
        try {
            final long size = file.getSize();
            if (size > FileUploadUtil.MAX_IMAGE_SIZE) { //2 MB
                throw new FuncErrorException("Max image file size is 2 MB");
            }

            final Map result = cloudinary.uploader()
                                         .upload(file.getBytes(),
                                                 Map.of("public_id",
                                                         "image/" + fileName));
            final String url      = (String) result.get("secure_url");
            final String publicId = (String) result.get("public_id");
            return CloudinaryResponse.builder().publicId(publicId).url(url).build();
        } catch (final Exception e) {
            throw new FuncErrorException("Failed to upload image file");
        }
    }

    @Transactional
    public CloudinaryResponse uploadPdfFile(final MultipartFile file, final String fileName) {
        try {
            final long size = file.getSize();
            if (size > FileUploadUtil.MAX_PDF_SIZE) { //20 MB
                throw new FuncErrorException("Max file size for PDFs is 20 MB");
            }

            final Map result = cloudinary.uploader()
                                         .upload(file.getBytes(),
                                                 Map.of("public_id",
                                                        "pdf/" + fileName));
            final String url      = (String) result.get("secure_url");
            final String publicId = (String) result.get("public_id");
            return CloudinaryResponse.builder().publicId(publicId).url(url).build();
        } catch (Exception e) {
            throw new FuncErrorException("Failed to upload PDF file");
        }
    }
}
