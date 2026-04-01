package com.pao.laboratory06.exercise2;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();

        List<Colaborator> colaboratori = new ArrayList<>();
        for (int i = 0; i < n; i++){
            String tip = in.next();
            Colaborator colaborator = null;

            switch (tip){
                case "CIM":
                    colaborator = new CIMColaborator();
                    break;
                case "PFA":
                    colaborator = new PFAColaborator();
                    break;
                case "SRL":
                    colaborator = new SRLColaborator();
                    break;
            }

            if (colaborator != null){
                colaborator.citeste(in);
                colaboratori.add(colaborator);
            }
        }

        for (Colaborator c : colaboratori){
            c.afiseaza();
        }

        System.out.println();

        Colaborator maxim = colaboratori.stream()
                .max(Comparator.comparingDouble(Colaborator::calculeazaVenitNetAnual))
                .orElse(null);

        if (maxim != null){
            System.out.println("Colaborator cu venit net maxim: " + maxim);
        }

        System.out.println();
        System.out.println("Colaboratori persoane juridice:");
        for (Colaborator c : colaboratori){
            if (c instanceof PersoanaJuridica){
                c.afiseaza();
            }
        }

        Double sumaCIM = null, sumaPFA = null, sumaSRL = null;
        Integer nrCIM = null, nrPFA = null, nrSRL = null;

        for (Colaborator c : colaboratori){
            switch (c.getTip()){
                case CIM:
                    if (sumaCIM == null){
                        sumaCIM = 0.0;
                        nrCIM = 0;
                    }
                    sumaCIM += c.calculeazaVenitNetAnual();
                    nrCIM++;
                    break;
                case PFA:
                    if (sumaPFA == null){
                        sumaPFA = 0.0;
                        nrPFA = 0;
                    }
                    sumaPFA += c.calculeazaVenitNetAnual();
                    nrPFA++;
                    break;
                case SRL:
                    if (sumaSRL == null){
                        sumaSRL = 0.0;
                        nrSRL = 0;
                    }
                    sumaSRL += c.calculeazaVenitNetAnual();
                    nrSRL++;
                    break;
            }
        }

        System.out.println();
        System.out.println("Sume și număr colaboratori pe tip:");

        if (sumaCIM == null){
            System.out.println("CIM: suma = nu lei, număr = null");
        }
        else
            System.out.printf("CIM: suma = %.2f lei, număr = %d%n", sumaCIM, nrCIM);

        if (sumaPFA == null){
            System.out.println("PFA: suma = nu lei, număr = null");
        }
        else
            System.out.printf("PFA: suma = %.2f lei, număr = %d%n", sumaPFA, nrPFA);

        if (sumaSRL == null){
            System.out.println("SRL: suma = nu lei, număr = null");
        }
        else
            System.out.printf("SRL: suma = %.2f lei, număr = %d%n", sumaSRL, nrSRL);
    }
}