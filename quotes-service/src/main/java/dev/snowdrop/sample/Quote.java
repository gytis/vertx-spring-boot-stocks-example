package dev.snowdrop.sample;

import java.time.Instant;

public class Quote {

    private String symbol;

    private double price;

    private Instant timestamp;

    private Quote() {
        // Used by encoders
    }

    public Quote(String symbol, double price, Instant timestamp) {
        this.symbol = symbol;
        this.price = price;
        this.timestamp = timestamp;
    }

    public String getSymbol() {
        return symbol;
    }

    public double getPrice() {
        return price;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return String.format("Quote{symbol='%s', price=%s, timestamp=`%s`}", symbol, price, timestamp);
    }
}
