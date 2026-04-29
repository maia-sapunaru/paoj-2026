package com.pao.laboratory09.exercise1;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main{
    private static final String OUTPUT_FILE = "output/lab09_ex1.ser";

    public static void main(String[] args) throws Exception{
        Scanner scanner = new Scanner(System.in);
        TranzactieService service = new TranzactieService();

        int n = scanner.nextInt();
        List<Tranzactie> tranzactii = new ArrayList<>();

        for (int i = 0; i < n; i++){
            int id = scanner.nextInt();
            double suma = scanner.nextDouble();
            String data = scanner.next();
            String contSursa = scanner.next();
            String contDestinatie = scanner.next();
            TipTranzactie tip = TipTranzactie.valueOf(scanner.next());

            Tranzactie tranzactie = new Tranzactie(id, suma, data, contSursa, contDestinatie, tip);

            tranzactie.setNote("procesat");
            tranzactii.add(tranzactie);
        }

        service.serializeaza(tranzactii, OUTPUT_FILE);
        List<Tranzactie> tranzactiiDeserializate = service.deserializeaza(OUTPUT_FILE);

        while (scanner.hasNext()){
            String comanda = scanner.next();

            if (comanda.equals("LIST")){
                afiseazaToate(tranzactiiDeserializate);
            } else if (comanda.equals("FILTER")){
                String prefix = scanner.next();
                filtreazaDupaLuna(tranzactiiDeserializate, prefix);
            } else if (comanda.equals("NOTE")){
                int id = scanner.nextInt();
                afiseazaNote(tranzactiiDeserializate, id);
            }
        }
    }

    private static void afiseazaToate(List<Tranzactie> tranzactii){
        for (Tranzactie tranzactie : tranzactii){
            System.out.println(tranzactie);
        }
    }

    private static void filtreazaDupaLuna(List<Tranzactie> tranzactii, String prefix){
        boolean gasit = false;

        for (Tranzactie tranzactie : tranzactii){
            if (tranzactie.getData().startsWith(prefix)){
                System.out.println(tranzactie);
                gasit = true;
            }
        }

        if (!gasit) {
            System.out.println("Niciun rezultat.");
        }
    }

    private static void afiseazaNote(List<Tranzactie> tranzactii, int id){
        for (Tranzactie tranzactie : tranzactii){
            if (tranzactie.getId() == id){
                System.out.println("NOTE[" + id + "]: " + tranzactie.getNote());
                return;
            }
        }

        System.out.println("NOTE[" + id + "]: not found");
    }
}