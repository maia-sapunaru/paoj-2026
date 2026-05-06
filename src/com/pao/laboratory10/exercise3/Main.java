package com.pao.laboratory10.exercise3;

import java.util.*;
import java.util.stream.Collectors;

public class Main{
    public static void main(String[] args){
        List<TranzactieDemo> tranzactii = List.of(
                new TranzactieDemo(1, 1500.00, "2024-01-15", "CREDIT", "CONT_A"),
                new TranzactieDemo(2, 750.50, "2024-01-22", "DEBIT", "CONT_B"),
                new TranzactieDemo(3, 200.00, "2024-02-05", "CREDIT", "CONT_A"),
                new TranzactieDemo(4, 1200.00, "2024-02-18", "DEBIT", "CONT_C"),
                new TranzactieDemo(5, 500.00, "2024-03-10", "CREDIT", "CONT_B"),
                new TranzactieDemo(6, 300.00, "2024-03-22", "DEBIT", "CONT_A"),
                new TranzactieDemo(7, 900.00, "2024-01-30", "CREDIT", "CONT_D"),
                new TranzactieDemo(8, 100.00, "2024-02-12", "DEBIT", "CONT_C"),
                new TranzactieDemo(9, 2500.00, "2024-03-01", "CREDIT", "CONT_A"),
                new TranzactieDemo(10, 80.00, "2024-04-03", "DEBIT", "CONT_E")
        );

        System.out.println("1. Tranzactii CREDIT:");
        tranzactii.stream()
                .filter(t -> t.getTip().equals("CREDIT"))
                .forEach(System.out::println);

        System.out.println();

        System.out.println("2. Total procesat:");
        double total = tranzactii.stream()
                .mapToDouble(TranzactieDemo::getSuma)
                .sum();
        System.out.printf("Total procesat: %.2f RON%n", total);

        System.out.println();

        System.out.println("3. Total per luna:");
        Map<String, Double> totalPerLuna = tranzactii.stream()
                .collect(Collectors.groupingBy(
                        t -> t.getData().substring(0, 7),
                        TreeMap::new,
                        Collectors.summingDouble(TranzactieDemo::getSuma)
                ));

        totalPerLuna.forEach((luna, suma) -> System.out.printf("%s: %.2f RON%n", luna, suma));

        System.out.println();

        System.out.println("4. Top 3 tranzactii:");
        tranzactii.stream()
                .sorted(Comparator.comparingDouble(TranzactieDemo::getSuma).reversed())
                .limit(3)
                .forEach(System.out::println);

        System.out.println();

        System.out.println("5. Conturi sursa unice:");
        List<String> conturiUnice = tranzactii.stream()
                .map(TranzactieDemo::getContSursa)
                .distinct()
                .collect(Collectors.toList());

        System.out.println("Conturi sursa unice: " + conturiUnice);

        System.out.println();

        System.out.println("6. Suma medie:");
        double medie = tranzactii.stream()
                .mapToDouble(TranzactieDemo::getSuma)
                .average()
                .orElse(0.0);

        System.out.printf("Suma medie: %.2f RON%n", medie);

        System.out.println();

        System.out.println("7. Extrase de cont lunare:");
        Map<String, List<TranzactieDemo>> tranzactiiPeLuna = tranzactii.stream()
                .collect(Collectors.groupingBy(
                        t -> t.getData().substring(0, 7),
                        TreeMap::new,
                        Collectors.toList()
                ));

        tranzactiiPeLuna.forEach((luna, lista) ->{
            double sumaLuna = lista.stream()
                    .mapToDouble(TranzactieDemo::getSuma)
                    .sum();

            System.out.printf(
                    "EXTRAS DE CONT - %s: %d tranzactii, total: %.2f RON%n",
                    luna, lista.size(), sumaLuna
            );
        });
    }

    static class TranzactieDemo{
        private int id;
        private double suma;
        private String data;
        private String tip;
        private String contSursa;

        public TranzactieDemo(int id, double suma, String data, String tip, String contSursa){
            this.id = id;
            this.suma = suma;
            this.data = data;
            this.tip = tip;
            this.contSursa = contSursa;
        }

        public double getSuma(){
            return suma;
        }

        public String getData(){
            return data;
        }

        public String getTip(){
            return tip;
        }

        public String getContSursa(){
            return contSursa;
        }

        @Override
        public String toString(){
            return String.format("[%d] %s %s: %.2f RON, cont sursa: %s",
                    id, data, tip, suma, contSursa);
        }
    }
}