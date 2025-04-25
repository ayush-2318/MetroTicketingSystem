package org.example.service;

import com.nimbusds.jose.util.Pair;
import org.example.model.QrDetailDto;
import org.example.request.TicketRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Service
public class ConvertToQrFormatService {
    public String convertToQrData(TicketRequest ticketRequest){
        String source=ticketRequest.getSourceStation();
        String destination=ticketRequest.getDestinationStation();
        Pair<String,String> pair=getTicketValidFromAndTill();
        QrDetailDto qrDetailDto=new QrDetailDto(source,destination, pair.getLeft(), pair.getRight());

        return qrDetailDto.QrDetailToString();
    }

    private Pair<String,String> getTicketValidFromAndTill(){
        LocalDateTime from=LocalDateTime.now();
        LocalDateTime till=from.plusHours(3);

        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("dd:MM:yyyy'T'HH:mm:ss");
        String fromFormatted=from.format(formatter);
        String tillFormatted=till.format(formatter);

        Pair<String,String> pair=Pair.of(fromFormatted,tillFormatted);
        return pair;
    }
}
