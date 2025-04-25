package org.example.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.example.model.JWTDto;
import org.example.request.TicketRequest;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class JWTService {

 // private static final String secretKey="eyJhbGciOiJIUzI1NiJ9.eyJSb2xlIjoiQWRtaW4iLCJJc3N1ZXIiOiJJc3N1ZXIiLCJVc2VybmFtZSI6IkphdmFJblVzZSIsImV4cCI6MTc0NTM0NDIyMiwiaWF0IjoxNzQ1MzQ0MjIyfQ.dmHEnCIbwVbkezQQFRRlwlT0-I6fm4q-NQsDNDIUMTc";
 public  static  final String SecretKey="357638792F423F4428472B4B6250655368566D597133743677397A2443264629357638792F423F4428472B4B6250655368566D597133743677397A2443264629";
  public String getJwtToken(TicketRequest ticketRequest){
    JWTDto jwtDto =new JWTDto(UUID.randomUUID().toString(),ticketRequest.getSourceStation(),ticketRequest.getDestinationStation(),false);
    Map<String, Object> claims=new HashMap<>();
    /*
    claims.put("from",jwtDto.getFrom());
    claims.put("to",jwtDto.getTo());
    claims.put("use",jwtDto.isUse());
    */

    claims.put("ticketInfo",jwtDto);
    return createJwtToken(claims);
  }

  private String createJwtToken(Map<String,Object> claims){
    return Jwts.builder()
            .setClaims(claims)
            .setExpiration(new Date(System.currentTimeMillis()+1000*60*1))
            .signWith(SignatureAlgorithm.HS256,SecretKey).compact();
  }

}
