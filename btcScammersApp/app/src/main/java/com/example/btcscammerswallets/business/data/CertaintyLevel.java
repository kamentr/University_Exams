package com.example.btcscammerswallets.business.data;

public enum CertaintyLevel {
    CERTAIN(3),
    HIGH(1.5),
    LOW(0.5),
    MEDIUM(1),
    UNCERTAIN(0.2);

    private final double value;

    CertaintyLevel(double value) {
        this.value = value;
    }

    public double getValue() {
        return this.value;
    }
}



