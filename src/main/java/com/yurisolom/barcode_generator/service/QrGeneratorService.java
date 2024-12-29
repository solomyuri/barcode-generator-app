package com.yurisolom.barcode_generator.service;

import com.yurisolom.barcode_generator.dto.QrByUrlRequest;

import java.awt.image.BufferedImage;

public interface QrGeneratorService {

    BufferedImage generateByUrl(QrByUrlRequest reqDto);
}
