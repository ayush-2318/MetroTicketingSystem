package org.example.service;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import org.example.entities.TicketInfo;
import org.example.model.TicketInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;

@Service
public class QrCodeDecoderService {
    @Autowired
    JWTParseService jwtParseService;

    public TicketInfoDto getTicketInfoFromQr(String qrCode) throws NotFoundException, IOException {
        String jwtToken=decodeQr(qrCode);
        TicketInfoDto ticketInfoDto=jwtParseService.getTicketInfoDto(jwtToken);
        return ticketInfoDto;
    }

    private String decodeQr(String qrCode) throws IOException, NotFoundException {
        byte[] qrBytes= Base64.getDecoder().decode(qrCode);
        ByteArrayInputStream inputStream=new ByteArrayInputStream(qrBytes);
        BufferedImage bufferedImage= ImageIO.read(inputStream);

        LuminanceSource source=new BufferedImageLuminanceSource(bufferedImage);
        BinaryBitmap binaryBitmap=new BinaryBitmap(new HybridBinarizer(source));
        Result result=new MultiFormatReader().decode(binaryBitmap);
        return result.getText();
    }
}
