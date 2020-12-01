package com.example.btcscammerswallets.business.data;

public enum SuspicionLevel {

    SMALL(5),
    MEDIUM(10),
    HIGH(20);

    private final int value;

    SuspicionLevel(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
