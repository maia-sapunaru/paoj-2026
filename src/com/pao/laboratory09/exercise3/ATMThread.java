package com.pao.laboratory09.exercise3;

import java.util.concurrent.atomic.AtomicInteger;

public class ATMThread extends Thread{
    private static final AtomicInteger ID_GENERATOR = new AtomicInteger(1);

    private final int atmId;
    private final CoadaTranzactii coada;

    public ATMThread(int atmId, CoadaTranzactii coada){
        this.atmId = atmId;
        this.coada = coada;
    }

    @Override
    public void run(){
        for (int i = 0; i < 4; i++){
            try {
                int id = ID_GENERATOR.getAndIncrement();
                double suma = 100.0 * id;
                String data = "2024-05-14";

                Tranzactie tranzactie = new Tranzactie(id, suma, data);

                System.out.printf("[ATM-%d] trimite: Tranzactie #%d %.2f RON%n",
                        atmId, tranzactie.getId(), tranzactie.getSuma());

                coada.adauga(tranzactie, "ATM-" + atmId);

                Thread.sleep(50);
            } catch (InterruptedException e){
                return;
            }
        }
    }
}