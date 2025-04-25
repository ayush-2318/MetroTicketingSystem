package org.example.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.request.TicketRequest;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@NoArgsConstructor
@AllArgsConstructor
public class QrDetailDto {
    @JsonProperty("sourceStation")
    private String sourceStation;
    @JsonProperty("destinationStation")
    private String destinationStation;
    @JsonProperty("validFrom")
    private String validFrom;
    @JsonProperty("validTill")
    private String validTill;

    public String QrDetailToString(){
        return String.format("sourceStation=%s|destinationStation=%s|validFrom=%s,validTill=%s",
                sourceStation,destinationStation,validFrom,validTill);
    }
}


