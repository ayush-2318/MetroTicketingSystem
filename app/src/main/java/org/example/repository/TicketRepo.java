package org.example.repository;

import org.example.entities.TicketInfo;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TicketRepo extends CrudRepository<TicketInfo,String> {
    Optional<TicketInfo> findByTicketId(String ticketId);
}
