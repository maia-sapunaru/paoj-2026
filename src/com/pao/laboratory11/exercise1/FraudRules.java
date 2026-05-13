package com.pao.laboratory11.exercise1;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

public final class FraudRules {
    public static final Set<String> HIGH_RISK_COUNTRIES =
            new HashSet<>(Arrays.asList("RU", "NG", "IR", "KP", "SY"));

    public static final Predicate<Transaction> AMOUNT_OVER_THRESHOLD =
            tx -> tx.getAmount() >= 1000.0;

    public static final Predicate<Transaction> COUNTRY_IN_RISK =
            tx -> HIGH_RISK_COUNTRIES.contains(tx.getCountry());

    public static final Predicate<Transaction> CHANNEL_SUSPICIOUS =
            tx -> tx.getChannel().equals("WEB") || tx.getChannel().equals("APP") || tx.getChannel().equals("CRYPTO");

    public static final Predicate<Transaction> SUSPICIOUS_RULE =
            AMOUNT_OVER_THRESHOLD.or(COUNTRY_IN_RISK).or(CHANNEL_SUSPICIOUS);

    private FraudRules() {}
}