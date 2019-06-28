package dev.snowdrop.sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_STREAM_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@EnableScheduling
@SpringBootApplication
public class QuotesServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuotesServiceApplication.class, args);
    }

    @Bean
    public RouterFunction<ServerResponse> router(QuotesHandler handler) {
        return route(GET("/").and(accept(APPLICATION_STREAM_JSON)), handler);
    }
}
