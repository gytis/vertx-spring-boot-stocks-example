package dev.snowdrop.sample;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_STREAM_JSON;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
public class QuotesHandler implements HandlerFunction<ServerResponse> {

    private final QuotesManager quotesManager;

    public QuotesHandler(QuotesManager quotesManager) {
        this.quotesManager = quotesManager;
    }

    @Override
    public Mono<ServerResponse> handle(ServerRequest serverRequest) {
        return ok()
                .contentType(APPLICATION_STREAM_JSON)
                .body(quotesManager.getQuotesStream(), Quote.class);
    }
}
