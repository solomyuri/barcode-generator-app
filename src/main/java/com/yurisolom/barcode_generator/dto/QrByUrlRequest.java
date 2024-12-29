package com.yurisolom.barcode_generator.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class QrByUrlRequest {

    @Pattern(regexp = "https?:\\/\\/(www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_\\+.~#?&//=]*)",
            message = "Incorrect parameter, url")
    @NotNull(message = "Required parameter, url")
    private String url;
    private Integer width = 500;
    private Integer height = 500;

}
