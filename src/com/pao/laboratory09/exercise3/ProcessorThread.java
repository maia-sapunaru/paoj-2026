package com.pao.laboratory09.exercise3;

import java.util.concurrent.atomic.AtomicInteger;

public class ProcessorThread implements Runnable{
    private final CoadaTranzactii coada;
    private final AtomicInteger totalProcesate = new AtomicInteger(0);

    private volatile boolean activ = true;

    public ProcessorThread(CoadaTranzactii coada){
        this.coada = coada;
    }

    public boolean isActiv(){
        return activ;
    }

    public void opreste(){
        activ = false;
    }

    public int getTotalProcesate(){
        return totalProcesate.get();
    }

    @Override
    public void run() {
        while (activ || true){
            try {
                Tranzactie tranzactie = coada.extrage(this);

                if (tranzactie == null){
                    break;
                }

                Thread.sleep(80);

                System.out.printf("[Processor] Factura #%d - %.2f RON | %s%n",
                        tranzactie.getId(), tranzactie.getSuma(), tranzactie.getData());

                totalProcesate.incrementAndGet();
            } catch (InterruptedException e){
                return;
            }
        }
    }
}