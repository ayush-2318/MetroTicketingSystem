package org.example.controller;

import org.example.request.TicketRequest;
import org.example.service.ConvertToQrFormatService;
import org.example.service.GenerateQrService;
import org.example.service.TicketPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class TicketBookingController {
    @Autowired
    private TicketPriceService ticketPriceService;

    @Autowired
    private ConvertToQrFormatService convertToQrFormatService;

    @Autowired
    private GenerateQrService generateQrService;


    @PostMapping("/bookTicket")
    public Mono<ResponseEntity> TicketBooking(@RequestBody TicketRequest ticketRequest){
        Mono<Boolean> isPaymentSuccessful=ticketPriceService.isPaymentSuccessful(ticketRequest);
    return   isPaymentSuccessful.map(
                success->{
                    if (success){
                        try {
                            String qrText= convertToQrFormatService.convertToQrData(ticketRequest);
                            String qrCode=generateQrService.generateQrAsByte(qrText,200,200);

                            return new ResponseEntity<>(qrCode,HttpStatus.OK);
                        }catch (Exception ex){
                            return new ResponseEntity<>("Not able to generate qr",HttpStatus.INTERNAL_SERVER_ERROR);
                        }

                    }else {
                        return new ResponseEntity<>("Not done",HttpStatus.INTERNAL_SERVER_ERROR);
                    }
                }
        );

    }
}
