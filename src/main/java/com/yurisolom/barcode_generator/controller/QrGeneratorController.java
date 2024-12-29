package com.yurisolom.barcode_generator.controller;

import com.yurisolom.barcode_generator.dto.QrByUrlRequest;
import com.yurisolom.barcode_generator.service.QrGeneratorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.image.BufferedImage;

@RestController
@RequestMapping("/v1/qrcode")
@RequiredArgsConstructor
public class QrGeneratorController {

    private final QrGeneratorService generatorService;

    @PostMapping(value = "/generateByUrl", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<BufferedImage> generateQrByUrl(@Valid @RequestBody QrByUrlRequest reqDto) {
        return ResponseEntity.ofNullable(generatorService.generateByUrl(reqDto));
    }
}
