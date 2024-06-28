package com.example.SWP391_Project.utils;

import com.example.SWP391_Project.exception.FuncErrorException;
import lombok.experimental.UtilityClass;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@UtilityClass
public class FileUploadUtil {

    public static final long MAX_IMAGE_SIZE = 2 * 1024 * 1024; // 2MB
    public static final long MAX_PDF_SIZE = 20 * 1024 * 1024; // 20MB

    public static final String IMAGE_PATTERN = "([^\\s]+(\\.(?i)(jpg|jpeg|png|gif|bmp))$)";

    public static final String PDF_PATTERN = "([^\\s]+(\\.(?i)(pdf))$)";

    public static final String DATE_FORMAT = "yyyyMMddHHmmss";

    public static final String FILE_NAME_FORMAT = "%s_%s";

    public static boolean isAllowedExtension(final String fileName, final String pattern) {
        final Matcher matcher = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE).matcher(fileName);
        return matcher.matches();
    }

    public static void assertAllowedImage(MultipartFile file) {
        final long size = file.getSize();
        if (size > MAX_IMAGE_SIZE) {
            throw new FuncErrorException("Max image file size is 2 MB");
        }

        final String fileName = file.getOriginalFilename();
        final String extension = FilenameUtils.getExtension(fileName);
        if (!isAllowedExtension(fileName, IMAGE_PATTERN)) {
            throw new FuncErrorException("Only jpg, png, gif, bmp files are allowed for images");
        }
    }

    public static void assertAllowedPDF(MultipartFile file) {
        final long size = file.getSize();
        if (size > MAX_PDF_SIZE) {
            throw new FuncErrorException("Max file size for PDFs is 20 MB");
        }

        final String fileName = file.getOriginalFilename();
        final String extension = FilenameUtils.getExtension(fileName);
        if (!isAllowedExtension(fileName, PDF_PATTERN)) {
            throw new FuncErrorException("Only pdf files are allowed");
        }
    }

    public static String getFileName(final String name) {
        final DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        final String date = dateFormat.format(System.currentTimeMillis());
        return String.format(FILE_NAME_FORMAT, name, date);
    }

}