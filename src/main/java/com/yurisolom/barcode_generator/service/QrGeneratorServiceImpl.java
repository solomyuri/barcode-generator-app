package com.yurisolom.barcode_generator.service;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import javax.imageio.ImageIO;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.yurisolom.barcode_generator.dto.QrByUrlRequest;
import com.yurisolom.barcode_generator.dto.QrByUrlWithPicRequest;
import com.yurisolom.barcode_generator.exception.AppException;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class QrGeneratorServiceImpl implements QrGeneratorService {

    @Override
    public BufferedImage generateByUrl(QrByUrlRequest reqDto) {
			BitMatrix bitMatrix = getQrBitMatrix(reqDto.getUrl(), reqDto.getWidth(), reqDto.getHeight());
            return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }

	@Override
	public BufferedImage generateByUrlWitPicture(QrByUrlWithPicRequest reqDto) {
		try (InputStream inputStream = reqDto.getFile().getInputStream()) {
			BufferedImage picture = ImageIO.read(inputStream);
			BitMatrix bitMatrix = getQrBitMatrix(reqDto.getUrl(), reqDto.getWidth(), reqDto.getHeight());
			BufferedImage qr = MatrixToImageWriter.toBufferedImage(bitMatrix);
			return addPictureToQRCode(qr, picture);
		} catch (IOException e) {
			log.error("Ошибка при генерации QR кода. {}", e.getMessage());
			throw AppException.builder()
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.message("Ошибка при обработке файла")
					.build();
		}
	}
	
	private BitMatrix getQrBitMatrix(String url, int width, int height) {
		final Map<EncodeHintType, ?> hints = Map.of(
				EncodeHintType.MARGIN, 1,
				EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
		try {
			return new QRCodeWriter().encode(url, BarcodeFormat.QR_CODE, width, height, hints);
		} catch (WriterException e) {
			log.error("Ошибка при генерации QR кода. {}", e.getMessage());
			throw AppException.builder()
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.message("Ошибка при генерации QR кода")
					.build();
		}
	}
	
	private BufferedImage addPictureToQRCode(BufferedImage qrImage, BufferedImage picture) {
		int minDimension = Math.min(qrImage.getWidth(), qrImage.getHeight());
		int pictureSize = minDimension / 4;
		Image scaledPicture = picture.getScaledInstance(pictureSize, pictureSize, Image.SCALE_SMOOTH);
		BufferedImage qrCombined = new BufferedImage(qrImage.getWidth(), qrImage.getHeight(),
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = qrCombined.createGraphics();
		g2d.drawImage(qrImage, 0, 0, null);
		int x = (qrImage.getWidth() - pictureSize) / 2;
		int y = (qrImage.getHeight() - pictureSize) / 2;
		g2d.drawImage(scaledPicture, x, y, null);
		g2d.dispose();
		return qrCombined;
	}

}
