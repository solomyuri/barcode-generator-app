package com.yurisolom.barcode_generator.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.yurisolom.barcode_generator.dto.QrByUrlRequest;
import com.yurisolom.barcode_generator.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;

@Service
@Slf4j
public class QrGeneratorServiceImpl implements QrGeneratorService {

    @Override
    public BufferedImage generateByUrl(QrByUrlRequest reqDto) {
        try {
            BitMatrix bitMatrix = new QRCodeWriter()
                    .encode(reqDto.getUrl(), BarcodeFormat.QR_CODE, reqDto.getWidth(), reqDto.getHeight());
            return MatrixToImageWriter.toBufferedImage(bitMatrix);
        } catch (WriterException e) {
            log.error("Ошибка при генерации QR кода. {}", e.getMessage());
            throw AppException.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .message("Ошибка при генерации QR кода")
                    .build();
        }
    }

}
