package com.pao.laboratory14.exercise1;

import java.util.*;
import java.util.stream.Collector;

public final class RaportVanzari {
    private final Map<TipBilet, Long> numarPerTip;
    private final Map<TipBilet, Double> incasariPerTip;
    private final double totalGlobal;
    private final double medieGlobala;
    private final TipBilet tipCelMaiPopular;

    private RaportVanzari(
            Map<TipBilet, Long> numarPerTip,
            Map<TipBilet, Double> incasariPerTip,
            double totalGlobal,
            double medieGlobala,
            TipBilet tipCelMaiPopular
    ) {
        this.numarPerTip = Collections.unmodifiableMap(numarPerTip);
        this.incasariPerTip = Collections.unmodifiableMap(incasariPerTip);
        this.totalGlobal = totalGlobal;
        this.medieGlobala = medieGlobala;
        this.tipCelMaiPopular = tipCelMaiPopular;
    }

    public static Collector<Bilet, ?, RaportVanzari> collector() {
        return Collector.of(
                AcumulatorRaport::new,
                AcumulatorRaport::adauga,
                AcumulatorRaport::combina,
                AcumulatorRaport::toRaport
        );
    }

    public Map<TipBilet, Long> getNumarPerTip() {
        return numarPerTip;
    }

    public Map<TipBilet, Double> getIncasariPerTip() {
        return incasariPerTip;
    }

    public double getTotalGlobal() {
        return totalGlobal;
    }

    public double getMedieGlobala() {
        return medieGlobala;
    }

    public TipBilet getTipCelMaiPopular() {
        return tipCelMaiPopular;
    }

    private static class AcumulatorRaport {
        private final Map<TipBilet, Long> count = new EnumMap<>(TipBilet.class);
        private final Map<TipBilet, Double> incasari = new EnumMap<>(TipBilet.class);

        void adauga(Bilet bilet) {
            TipBilet tip = bilet.getTip();
            count.put(tip, count.getOrDefault(tip, 0L) + 1);
            incasari.put(tip, incasari.getOrDefault(tip, 0.0) + bilet.getPret());
        }

        AcumulatorRaport combina(AcumulatorRaport other) {
            for (TipBilet tip : TipBilet.values()) {
                long totalCount = count.getOrDefault(tip, 0L) + other.count.getOrDefault(tip, 0L);
                double totalIncasari = incasari.getOrDefault(tip, 0.0) + other.incasari.getOrDefault(tip, 0.0);

                if (totalCount > 0) {
                    count.put(tip, totalCount);
                    incasari.put(tip, totalIncasari);
                }
            }

            return this;
        }

        RaportVanzari toRaport() {
            long totalBilete = count.values().stream()
                    .mapToLong(Long::longValue)
                    .sum();

            double totalIncasari = incasari.values().stream()
                    .mapToDouble(Double::doubleValue)
                    .sum();

            double medie = totalBilete == 0 ? 0.0 : totalIncasari / totalBilete;

            TipBilet popular = null;
            long maxCount = -1;

            for (TipBilet tip : TipBilet.values()) {
                long currentCount = count.getOrDefault(tip, 0L);

                if (currentCount > maxCount) {
                    maxCount = currentCount;
                    popular = tip;
                }
            }

            return new RaportVanzari(
                    new EnumMap<>(count),
                    new EnumMap<>(incasari),
                    totalIncasari,
                    medie,
                    popular
            );
        }
    }
}