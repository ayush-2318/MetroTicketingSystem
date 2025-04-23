package org.example.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.example.model.TicketInfoDto;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class JWTParseService {
    private   static  final String SecretKey="357638792F423F4428472B4B6250655368566D597133743677397A2443264629357638792F423F4428472B4B6250655368566D597133743677397A2443264629";

    public TicketInfoDto getTicketInfoDto(String jwtToken){
        Map<String ,Object> ticketInfoMap=parseJwt(jwtToken);
        ObjectMapper objectMapper=new ObjectMapper();
        TicketInfoDto ticketInfoDto=objectMapper.convertValue(ticketInfoMap,TicketInfoDto.class);
        return ticketInfoDto;
    }

    private Map<String, Object> parseJwt(String jwtToken){
        Claims claims= Jwts.parser()
                .setSigningKey(SecretKey)
                .build()
                .parseClaimsJws(jwtToken)
                .getBody();
        return (Map<String, Object>) claims.get("ticketInfo");
    }


}
