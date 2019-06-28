package dev.snowdrop.sample;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@RestController
public class QuotesController {

    private static final String QUOTES_SERVICE_URL = "http://localhost:8081";

    private final WebClient webClient;

    public QuotesController(WebClient.Builder webClientBuilder) {
        webClient = webClientBuilder
                .baseUrl(QUOTES_SERVICE_URL)
                .build();
    }

    @GetMapping(path = "/quotes", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Quote> getQuotes() {
        return webClient.get()
                .accept(MediaType.APPLICATION_STREAM_JSON)
                .retrieve()
                .bodyToFlux(Quote.class)
                .log();
    }
}
