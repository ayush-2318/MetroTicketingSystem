package org.example.service;

import org.example.request.TicketRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class TicketPriceService {
    @Autowired
    private CallPaymentService callPaymentService;
    /*
    private Long validateStationAndCalculateDistance(TicketRequest ticketRequest){
         String sourceStation= ticketRequest.getSourceStation();
         String destinationStation=ticketRequest.getDestinationStation();
        // dbquery
    }

     */

    public Mono<Boolean> isPaymentSuccessful(TicketRequest ticketRequest){
        //Long distance=validateStationAndCalculateDistance(ticketRequest);
        int amount=100;
        //call payment service
        Mono<Boolean> paymentSuccessful=callPaymentService.initiatePayment(amount);
        return paymentSuccessful;
    }

}
