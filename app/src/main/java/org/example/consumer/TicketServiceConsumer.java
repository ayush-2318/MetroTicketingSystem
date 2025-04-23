package org.example.consumer;

import org.example.service.JWTParseService;
import org.example.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class TicketServiceConsumer {
    @Autowired
    TicketService ticketService;
    @KafkaListener(topics = "${spring.kafka.topic.name}" , groupId = "${spring.kafka.consumer.group-id}")
    public void listen(String eventData){
        try {
            ticketService.saveTicketInfoToDb(eventData);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
