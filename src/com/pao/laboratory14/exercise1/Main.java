package com.pao.laboratory14.exercise1;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt();
        List<Bilet> bilete = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            int id = scanner.nextInt();
            String eveniment = scanner.next();
            TipBilet tip = TipBilet.valueOf(scanner.next());
            double pret = scanner.nextDouble();

            bilete.add(new Bilet(id, eveniment, tip, pret));
        }

        String comanda = scanner.next();

        RaportVanzari raport = bilete.stream()
                .collect(RaportVanzari.collector());

        afiseazaRaportSimplu(raport);

        if (comanda.equals("RAPORT_COMPLET")) {
            System.out.println("---");
            System.out.printf("Total: %.2f RON%n", raport.getTotalGlobal());
            System.out.printf("Medie: %.2f RON%n", raport.getMedieGlobala());
            System.out.println("Cel mai popular: " + raport.getTipCelMaiPopular());
        }
    }

    private static void afiseazaRaportSimplu(RaportVanzari raport) {
        for (TipBilet tip : TipBilet.values()) {
            if (raport.getNumarPerTip().containsKey(tip)) {
                System.out.printf(
                        "%s: count=%d incasari=%.2f RON%n",
                        tip,
                        raport.getNumarPerTip().get(tip),
                        raport.getIncasariPerTip().get(tip)
                );
            }
        }
    }
}