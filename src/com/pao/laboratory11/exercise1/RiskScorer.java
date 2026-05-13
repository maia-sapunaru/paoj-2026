package com.pao.laboratory11.exercise1;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

public final class RiskScorer {
    public static final int FLAG_THRESHOLD = 60;

    private static final Map<String, Integer> CHANNEL_SCORE = new HashMap<>();

    static {
        CHANNEL_SCORE.put("WEB", 15);
        CHANNEL_SCORE.put("APP", 10);
        CHANNEL_SCORE.put("CRYPTO", 30);
        CHANNEL_SCORE.put("POS", 5);
        CHANNEL_SCORE.put("ATM", 0);
    }

    public static final Function<Transaction, Integer> SCORE_FUNCTION =
            RiskScorer::riskScore;

    public static final Predicate<Transaction> FLAGGED_RULE =
            tx -> SCORE_FUNCTION.apply(tx) >= FLAG_THRESHOLD;

    public static int riskScore(Transaction tx) {
        int score = 0;

        if (tx.getAmount() >= 5000.0) {
            score += 70;
        } else if (tx.getAmount() >= 1000.0) {
            score += 40;
        } else if (tx.getAmount() >= 500.0) {
            score += 20;
        }

        if (tx.getAmount() <= 100.0) {
            score += 5;
        }

        if (FraudRules.HIGH_RISK_COUNTRIES.contains(tx.getCountry())) {
            score += 25;
        }

        score += CHANNEL_SCORE.getOrDefault(tx.getChannel(), 0);
        return score;
    }

    public static Verdict verdict(Transaction tx) {
        return FLAGGED_RULE.test(tx) ? Verdict.FLAG : Verdict.ALLOW;
    }

    private RiskScorer() {
    }
}