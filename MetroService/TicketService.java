package org.example.service;

import org.example.entities.TicketInfo;
import org.example.model.TicketInfoDto;
import org.example.repository.TicketRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

@Service
public class TicketService {
    @Autowired
    JWTParseService jwtParseService;

    @Autowired
    TicketRepo ticketRepo;
    public void saveTicketInfoToDb(String jwtToken){
        TicketInfoDto ticketInfoDto=jwtParseService.getTicketInfoDto(jwtToken);
        TicketInfo ticketInfo=TicketInfo.builder()
                .ticketId(ticketInfoDto.getTicketId())
                .sourceStation(ticketInfoDto.getFrom())
                .destinationStation(ticketInfoDto.getTo())
                .used(ticketInfoDto.isUse())
                .build();
        creatOrUpdateTicket(ticketInfo);
    }

    public void updateTicketUsed(TicketInfo ticketInfo){
        TicketInfo ticketInfoNew=ticketInfo.toBuilder()
                .used(true).build();
        creatOrUpdateTicket(ticketInfoNew);
    }

    public Optional<TicketInfo> getTicketInfo(String ticketId){
        return ticketRepo.findByTicketId(ticketId);
    }

    private void creatOrUpdateTicket(TicketInfo ticketInfo){
             boolean use=ticketInfo.isUsed();
        Function<TicketInfo,TicketInfo> updatingTicket=ticket->{
            if(use){
                ticket.setUsed(true);
            }
                return ticketRepo.save(ticket);
        };

        Supplier<TicketInfo> createUser=()->{
            return ticketRepo.save(ticketInfo);
        };

        TicketInfo ticketInfoFromDb=ticketRepo.findByTicketId(ticketInfo.getTicketId())
                .map(updatingTicket)
                .orElseGet(createUser);
    }



}
