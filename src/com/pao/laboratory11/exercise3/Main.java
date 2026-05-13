package com.pao.laboratory11.exercise3;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        List<Transaction> transactions = List.of(
                new Transaction(1, new BigDecimal("200.00"), LocalDate.of(2026, 5, 1), "RO", "WEB"),
                new Transaction(2, new BigDecimal("300.00"), LocalDate.of(2026, 5, 2), "RO", "ATM"),
                new Transaction(3, new BigDecimal("50.00"), LocalDate.of(2026, 5, 10), "NL", "APP"),
                new Transaction(4, new BigDecimal("900.00"), LocalDate.of(2026, 6, 2), "RO", "WEB"),
                new Transaction(5, new BigDecimal("900.00"), LocalDate.of(2026, 6, 3), "DE", "CRYPTO"),
                new Transaction(6, new BigDecimal("120.00"), LocalDate.of(2026, 6, 4), "DE", "APP")
        );

        AnalyticsSnapshot snapshot = transactions.stream()
                .collect(SnapshotCollectors.toSnapshot(3));

        System.out.println("TOTAL_AMOUNT");
        System.out.println(snapshot.getTotalAmount());

        System.out.println();

        System.out.println("TOP_TRANSACTIONS");
        snapshot.getTopTransactions()
                .forEach(System.out::println);

        System.out.println();

        System.out.println("COUNT_BY_COUNTRY");
        snapshot.getCountByCountry().entrySet().stream()
                .sorted(
                        Map.Entry.<String, Long>comparingByValue(Comparator.reverseOrder())
                                .thenComparing(Map.Entry.comparingByKey())
                )
                .forEach(entry -> System.out.println(entry.getKey() + " " + entry.getValue()));

        System.out.println();

        System.out.println("COUNT_BY_CHANNEL");
        snapshot.getCountByChannel().entrySet().stream()
                .sorted(
                        Map.Entry.<String, Long>comparingByValue(Comparator.reverseOrder())
                                .thenComparing(Map.Entry.comparingByKey())
                )
                .forEach(entry -> System.out.println(entry.getKey() + " " + entry.getValue()));
    }
}