package com.example.btcscammerswallets.business.data;

public class ScammerScore {

    int score;

    public ScammerScore(int initialScore) {
        score = initialScore;
    }

    public ScammerScore() {
        score = 0;
    }

    public void add(SuspicionLevel suspicionLevel) {
        score += suspicionLevel.getValue();
    }

    public void addMultiple(int amount, SuspicionLevel suspicionLevel) {
        for (int i = 0; i < amount; i++) {
            add(suspicionLevel);
        }
    }

    public int getFinalScore() {
        return getFinalScore(CertaintyLevel.MEDIUM);
    }

    public int getFinalScore(CertaintyLevel certaintyLevel) {
        return (int) Math.ceil(score * certaintyLevel.getValue());
    }
}



