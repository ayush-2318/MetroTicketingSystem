package org.example.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TicketRequest {

    @JsonProperty("sourceStation")
    public String sourceStation;
    @JsonProperty("destinationStation")
    public String destinationStation;
}
