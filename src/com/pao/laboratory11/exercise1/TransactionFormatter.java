package com.pao.laboratory11.exercise1;

public final class TransactionFormatter {
    public static String formatCheck(Transaction tx) {
        int score = RiskScorer.riskScore(tx);
        return "CHECK " + tx.getId() + " => " + RiskScorer.verdict(tx) + " score=" + score;
    }

    public static String formatRiskLine(Transaction tx) {
        int score = RiskScorer.riskScore(tx);
        return "[" + tx.getId() + "] " + RiskScorer.verdict(tx) + " score=" + score;
    }

    private TransactionFormatter() {
    }
}