package com.yurisolom.barcode_generator.service;

import com.yurisolom.barcode_generator.dto.QrByUrlRequest;
import com.yurisolom.barcode_generator.dto.QrByUrlWithPicRequest;

import java.awt.image.BufferedImage;

public interface QrGeneratorService {

    BufferedImage generateByUrl(QrByUrlRequest reqDto);
    BufferedImage generateByUrlWitPicture(QrByUrlWithPicRequest reqDto);
}
