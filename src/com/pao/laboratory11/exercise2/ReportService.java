package com.pao.laboratory11.exercise2;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

public final class ReportService {
    public static void reportMonth(List<ReportTransaction> transactions, String month) {
        double total = transactions.stream()
                .filter(tx -> tx.getDate().startsWith(month))
                .mapToDouble(ReportTransaction::getAmount)
                .sum();

        long count = transactions.stream()
                .filter(tx -> tx.getDate().startsWith(month))
                .count();

        System.out.printf(Locale.US, "MONTH %s total=%.2f count=%d%n", month, total, count);
    }

    public static void reportAccount(List<ReportTransaction> transactions, String accountId) {
        double total = transactions.stream()
                .filter(tx -> tx.getAccountId().equals(accountId))
                .mapToDouble(ReportTransaction::getAmount)
                .sum();

        long count = transactions.stream()
                .filter(tx -> tx.getAccountId().equals(accountId))
                .count();

        System.out.printf(Locale.US, "ACCOUNT %s total=%.2f count=%d%n", accountId, total, count);
    }

    public static void topChannels(List<ReportTransaction> transactions, int k) {
        Map<String, Long> counts = transactions.stream()
                .collect(Collectors.groupingBy(
                        ReportTransaction::getChannel,
                        Collectors.counting()
                ));

        if (counts.isEmpty()) {
            System.out.println("NONE");
            return;
        }

        counts.entrySet().stream().sorted(Map.Entry.<String, Long>comparingByValue(Comparator.reverseOrder()).thenComparing(Map.Entry.comparingByKey()))
                .limit(Math.max(0, k))
                .forEach(entry -> System.out.println(entry.getKey() + " " + entry.getValue()));
    }

    private ReportService() {
    }
}