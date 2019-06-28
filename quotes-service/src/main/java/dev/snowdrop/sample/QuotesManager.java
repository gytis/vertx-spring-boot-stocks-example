package dev.snowdrop.sample;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
public class QuotesManager {

    private final Random random = new Random();

    private final Map<String, Quote> quotesMap = new ConcurrentHashMap<>();

    public QuotesManager() {
        initQuotes();
    }

    public Flux<Quote> getQuotesStream() {
        return Flux.interval(Duration.ofSeconds(1))
                .flatMap(i -> Flux.fromIterable(quotesMap.values()))
                .onBackpressureDrop()
                .log();
    }

    @Scheduled(fixedRate = 1000)
    private void update() {
        Instant timestamp = Instant.now();

        quotesMap.forEach((symbol, quote) -> {
            double newPrice = getNewPrice(quote.getPrice());
            quotesMap.put(symbol, new Quote(symbol, newPrice, timestamp));
        });
    }

    private double getNewPrice(double currentPrice) {
        BigDecimal changeCoefficient = new BigDecimal(0.05 * (random.nextDouble() - 0.5));
        BigDecimal delta = new BigDecimal(currentPrice)
                .multiply(changeCoefficient);

        return new BigDecimal(currentPrice)
                .add(delta)
                .setScale(2, BigDecimal.ROUND_HALF_UP)
                .doubleValue();
    }

    private void initQuotes() {
        Instant timestamp = Instant.now();
        quotesMap.put("RHT", new Quote("RHT", 187.53, timestamp));
        quotesMap.put("IBM", new Quote("IBM", 138.36, timestamp));
        quotesMap.put("MSFT", new Quote("MSFT", 133.43, timestamp));
        quotesMap.put("ORCL", new Quote("ORCL", 56.07, timestamp));
    }
}
