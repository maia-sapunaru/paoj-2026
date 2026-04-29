package com.pao.laboratory09.exercise2;

import com.pao.laboratory09.exercise1.TipTranzactie;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main{
    private static final String OUTPUT_FILE = "output/lab09_ex2.bin";

    public static void main(String[] args) throws Exception{
        Scanner scanner = new Scanner(System.in);
        RegistruBinarService service = new RegistruBinarService();

        int n = scanner.nextInt();
        List<InregistrareTranzactie> tranzactii = new ArrayList<>();

        for (int i = 0; i < n; i++){
            int id = scanner.nextInt();
            double suma = scanner.nextDouble();
            String data = scanner.next();
            TipTranzactie tip = TipTranzactie.valueOf(scanner.next());

            tranzactii.add(new InregistrareTranzactie(id, suma, data, tip, StatusTranzactie.PENDING));
        }

        service.scrieInitial(tranzactii, OUTPUT_FILE);

        while (scanner.hasNext()){
            String comanda = scanner.next();

            if (comanda.equals("READ")){
                int idx = scanner.nextInt();

                InregistrareTranzactie tranzactie = service.citeste(OUTPUT_FILE, idx);
                System.out.println(tranzactie.format(idx));
            }
            else if (comanda.equals("UPDATE")){
                int idx = scanner.nextInt();
                StatusTranzactie status = StatusTranzactie.valueOf(scanner.next());

                service.actualizeazaStatus(OUTPUT_FILE, idx, status);
                System.out.println("Updated [" + idx + "]: " + status);
            }
            else if (comanda.equals("PRINT_ALL")){
                for (int idx = 0; idx < n; idx++){
                    InregistrareTranzactie tranzactie = service.citeste(OUTPUT_FILE, idx);
                    System.out.println(tranzactie.format(idx));
                }
            }
        }
    }
}