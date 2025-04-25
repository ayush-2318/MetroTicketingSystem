package org.example.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
@Slf4j
@Service
public class CallPaymentService {
    @Autowired
    private WebClient.Builder webClientBuilder;

    public Mono<Boolean> initiatePayment(int amount){
        return webClientBuilder.build()
                .post()
                .uri(
                        uriBuilder ->uriBuilder
                                .scheme("http")
                                .host("localhost")
                                .port(9892)
                                .path("/payment/create-order")
                                .queryParam("amount",amount)
                                .build())
                .exchangeToMono(response->{
                    if(response.statusCode().is2xxSuccessful()){
                        return Mono.just(true);
                    }else{
                        return Mono.just(false);
                    }
                })
                .timeout(Duration.ofSeconds(500))
                .doOnError(e -> log.error("Payment request failed: {}"));
    }
}
