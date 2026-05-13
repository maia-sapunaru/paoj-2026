package com.pao.laboratory11.exercise3;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;

public final class SnapshotCollectors {
    public static Collector<Transaction, ?, AnalyticsSnapshot> toSnapshot(int topN) {
        class Accumulator {
            private final Map<String, Long> countByCountry = new HashMap<>();
            private final Map<String, Long> countByChannel = new HashMap<>();
            private BigDecimal totalAmount = BigDecimal.ZERO;
            private final List<Transaction> allTransactions = new ArrayList<>();

            private void add(Transaction tx) {
                countByCountry.merge(tx.getCountry(), 1L, Long::sum);
                countByChannel.merge(tx.getChannel(), 1L, Long::sum);
                totalAmount = totalAmount.add(tx.getAmount());
                allTransactions.add(tx);
            }

            private Accumulator combine(Accumulator other) {
                other.countByCountry.forEach(
                        (country, count) -> countByCountry.merge(country, count, Long::sum)
                );

                other.countByChannel.forEach(
                        (channel, count) -> countByChannel.merge(channel, count, Long::sum)
                );

                totalAmount = totalAmount.add(other.totalAmount);
                allTransactions.addAll(other.allTransactions);

                return this;
            }

            private AnalyticsSnapshot finish() {
                List<Transaction> topTransactions = allTransactions.stream()
                        .sorted(
                                Comparator.comparing(Transaction::getAmount).reversed()
                                        .thenComparingInt(Transaction::getId)
                        )
                        .limit(Math.max(0, topN))
                        .toList();

                return new AnalyticsSnapshot(
                        countByCountry,
                        countByChannel,
                        totalAmount,
                        topTransactions
                );
            }
        }

        return Collector.of(Accumulator::new, Accumulator::add, Accumulator::combine, Accumulator::finish);
    }

    private SnapshotCollectors() {
    }
}