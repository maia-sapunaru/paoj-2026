package com.pao.laboratory09.exercise3;

import java.util.LinkedList;
import java.util.Queue;

public class CoadaTranzactii{
    private static final int CAPACITATE = 5;
    private final Queue<Tranzactie> coada = new LinkedList<>();

    public synchronized void adauga(Tranzactie tranzactie, String numeATM)
            throws InterruptedException{
        while (coada.size() == CAPACITATE){
            System.out.println("[" + numeATM + "] astept loc...");
            wait();
        }

        coada.add(tranzactie);
        notifyAll();
    }

    public synchronized Tranzactie extrage(ProcessorThread processor)
            throws InterruptedException{
        while (coada.isEmpty()) {
            if (!processor.isActiv()){
                return null;
            }

            wait();
        }

        Tranzactie tranzactie = coada.poll();
        notifyAll();

        return tranzactie;
    }

    public synchronized void trezesteToateFirele(){
        notifyAll();
    }
}