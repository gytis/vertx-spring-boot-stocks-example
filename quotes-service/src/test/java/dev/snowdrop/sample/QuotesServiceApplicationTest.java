package dev.snowdrop.sample;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class QuotesServiceApplicationTest {

    @Autowired
    private WebTestClient client;

    @Test
    public void shouldGetQuotesStream() {
        Flux<Quote> result = client.get()
                .exchange()
                .returnResult(Quote.class)
                .getResponseBody();

        StepVerifier.create(result)
                .thenRequest(5)
                .expectNextCount(5)
                .thenRequest(1)
                .expectNextCount(1)
                .thenCancel()
                .verify();
    }
}