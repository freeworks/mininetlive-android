package com.kouchen.mininetlive.pay;

/**
 * Created by cainli on 16/6/21.
 */
public class PaymentRequest {
    String channel;
    int amount;

    public PaymentRequest(String channel, int amount) {
        this.channel = channel;
        this.amount = amount;
    }
}