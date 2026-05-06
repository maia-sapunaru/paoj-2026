package com.pao.laboratory10.exercise2;

import com.pao.laboratory10.exercise1.Tranzactie;
import com.pao.laboratory10.exercise1.TipTranzactie;

import java.util.*;

public class Main{
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        ArrayList<Tranzactie> lista = new ArrayList<>();

        int n = scanner.nextInt();

        for (int i = 0; i < n; i++){
            int id = scanner.nextInt();
            double suma = scanner.nextDouble();
            String data = scanner.next();
            TipTranzactie tip = TipTranzactie.valueOf(scanner.next());

            lista.add(new Tranzactie(id, suma, data, tip));
        }

        while (scanner.hasNext()){
            String comanda = scanner.next();

            switch (comanda){
                case "UNIQUE_IDS":{
                    LinkedHashSet<Integer> ids = new LinkedHashSet<>();

                    for (Tranzactie t : lista){
                        ids.add(t.getId());
                    }

                    System.out.println("IDs unice (" + ids.size() + "): " + ids);
                    break;
                }

                case "MONTHLY_REPORT":{
                    TreeMap<String, double[]> raport = new TreeMap<>();

                    for (Tranzactie t : lista){
                        String luna = t.getData().substring(0, 7);
                        raport.putIfAbsent(luna, new double[2]);

                        if (t.getTip() == TipTranzactie.CREDIT){
                            raport.get(luna)[0] += t.getSuma();
                        } else {
                            raport.get(luna)[1] += t.getSuma();
                        }
                    }

                    for (Map.Entry<String, double[]> entry : raport.entrySet()){
                        double credit = entry.getValue()[0];
                        double debit = entry.getValue()[1];

                        System.out.printf(
                                "%s: CREDIT %.2f RON, DEBIT %.2f RON%n",
                                entry.getKey(), credit, debit
                        );
                    }
                    break;
                }

                case "TOP":{
                    int topN = scanner.nextInt();

                    ArrayList<Tranzactie> copie = new ArrayList<>(lista);
                    Collections.sort(copie, Comparator.comparingDouble(Tranzactie::getSuma).reversed());

                    System.out.println("Top " + topN + ":");

                    for (int i = 0; i < Math.min(topN, copie.size()); i++){
                        System.out.println(copie.get(i));
                    }
                    break;
                }

                case "SORT_ASC":{
                    Collections.sort(lista, Comparator.comparingDouble(Tranzactie::getSuma));
                    afiseazaLista(lista);
                    break;
                }

                case "SORT_DESC":{
                    Collections.sort(lista, Comparator.comparingDouble(Tranzactie::getSuma).reversed());
                    afiseazaLista(lista);
                    break;
                }

                case "REVERSE":{
                    Collections.reverse(lista);
                    afiseazaLista(lista);
                    break;
                }

                case "MIN_MAX":{
                    Comparator<Tranzactie> comparator = Comparator.comparingDouble(Tranzactie::getSuma);

                    Tranzactie min = Collections.min(lista, comparator);
                    Tranzactie max = Collections.max(lista, comparator);

                    System.out.println("MIN: " + min);
                    System.out.println("MAX: " + max);
                    break;
                }

                case "CME_DEMO":{
                    try {
                        for (Tranzactie t : lista){
                            lista.remove(t);
                        }
                    } catch (ConcurrentModificationException e){
                        System.out.println("ConcurrentModificationException prins: modificare in iteratie detectata.");
                    }
                    break;
                }
            }
        }
    }

    private static void afiseazaLista(ArrayList<Tranzactie> lista){
        for (Tranzactie t : lista) {
            System.out.println(t);
        }
    }
}