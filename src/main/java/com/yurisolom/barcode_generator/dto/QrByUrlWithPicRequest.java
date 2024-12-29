package com.yurisolom.barcode_generator.dto;

import org.springframework.web.multipart.MultipartFile;

import com.yurisolom.barcode_generator.validation.FileSize;
import com.yurisolom.barcode_generator.validation.FileType;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class QrByUrlWithPicRequest {

    @Pattern(regexp = "https?:\\/\\/(www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_\\+.~#?&//=]*)",
            message = "Incorrect parameter, url")
    @NotNull(message = "Required parameter, url")
    private String url;
    private Integer width = 500;
    private Integer height = 500;
    @NotNull(message = "Required parameter, file")
    @FileSize(max = 1024 * 1024, message = "File size must not more 1Mb")
    @FileType(allowed = {"image/png", "image/jpeg"}, message = "File type must be: PNG, JPG")
    private MultipartFile file;
    
}
