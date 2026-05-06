package com.pao.laboratory10.exercise1;

import java.util.*;

public class Main{
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        LinkedList<Tranzactie> coada = new LinkedList<>();

        while (scanner.hasNext()){
            String comanda = scanner.next();

            switch (comanda){
                case "ENQUEUE":{
                    int id = scanner.nextInt();
                    double suma = scanner.nextDouble();
                    String data = scanner.next();
                    TipTranzactie tip = TipTranzactie.valueOf(scanner.next());

                    coada.addLast(new Tranzactie(id, suma, data, tip));
                    break;
                }

                case "DEQUEUE":{
                    if (coada.isEmpty()){
                        System.out.println("Coada goala.");
                    } else{
                        Tranzactie t = coada.removeFirst();
                        System.out.println("Procesat: " + t);
                    }
                    break;
                }

                case "PUSH":{
                    int id = scanner.nextInt();
                    double suma = scanner.nextDouble();
                    String data = scanner.next();
                    TipTranzactie tip = TipTranzactie.valueOf(scanner.next());

                    coada.addFirst(new Tranzactie(id, suma, data, tip));
                    break;
                }

                case "POP":{
                    if (coada.isEmpty()){
                        System.out.println("Coada goala.");
                    }
                    else{
                        Tranzactie t = coada.removeFirst();
                        System.out.println("Extras: " + t);
                    }
                    break;
                }

                case "REMOVE_DEBIT":{
                    int eliminat = 0;
                    Iterator<Tranzactie> iterator = coada.iterator();

                    while (iterator.hasNext()){
                        Tranzactie t = iterator.next();

                        if (t.getTip() == TipTranzactie.DEBIT){
                            iterator.remove();
                            eliminat++;
                        }
                    }

                    System.out.println("Eliminat " + eliminat + " tranzactii DEBIT.");
                    break;
                }

                case "REMOVE_BELOW":{
                    double threshold = scanner.nextDouble();
                    int eliminat = 0;
                    Iterator<Tranzactie> iterator = coada.iterator();

                    while (iterator.hasNext()){
                        Tranzactie t = iterator.next();

                        if (t.getSuma() < threshold){
                            iterator.remove();
                            eliminat++;
                        }
                    }

                    System.out.printf("Eliminat %d tranzactii sub %.2f RON.%n", eliminat, threshold);
                    break;
                }

                case "PRINT":{
                    for (Tranzactie t : coada){
                        System.out.println(t);
                    }
                    break;
                }

                case "SIZE":{
                    System.out.println("Dimensiune coada: " + coada.size());
                    break;
                }
            }
        }
    }
}