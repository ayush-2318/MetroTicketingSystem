package org.example.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.example.eventProducer.QrDetailProducer;
import org.example.request.TicketRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

@Service
public class GenerateQrService {
    @Autowired
    private QrDetailProducer qrDetailProducer;

    /*
    @Autowired
    private ConvertToQrFormatService convertToQrFormatService;
    */

    @Autowired
    private JWTService jwtService;

    public String generateQrAsBase64(TicketRequest ticketRequest) throws
            WriterException, IOException{
        //String qrText=convertToQrFormatService.convertToQrData(ticketRequest);
        String qrText=jwtService.getJwtToken(ticketRequest);
        QRCodeWriter qrCodeWriter=new QRCodeWriter();
        BitMatrix bitMatrix=qrCodeWriter.encode(qrText, BarcodeFormat.QR_CODE,200,200);
        ByteArrayOutputStream outputStream=new ByteArrayOutputStream();

        MatrixToImageConfig config=new MatrixToImageConfig(0xFF000002 , 0xFFFFC041);
        MatrixToImageWriter.writeToStream(bitMatrix,"PNG",outputStream,config);

        byte[] qrByteArray=outputStream.toByteArray();

        String qrCode= Base64.getEncoder().encodeToString(qrByteArray);

        //qrCode send to kafka
        qrDetailProducer.sendEventToKafka(qrCode);

        return qrCode;

    }
}
