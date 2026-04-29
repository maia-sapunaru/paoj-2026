package com.pao.laboratory09.exercise2;

import com.pao.laboratory09.exercise1.TipTranzactie;

import java.util.Locale;

public class InregistrareTranzactie{
    private int id;
    private double suma;
    private String data;
    private TipTranzactie tip;
    private StatusTranzactie status;

    public InregistrareTranzactie(int id, double suma, String data, TipTranzactie tip, StatusTranzactie status){
        this.id = id;
        this.suma = suma;
        this.data = data;
        this.tip = tip;
        this.status = status;
    }

    public int getId(){
        return id;
    }

    public double getSuma(){
        return suma;
    }

    public String getData(){
        return data;
    }

    public TipTranzactie getTip(){
        return tip;
    }

    public StatusTranzactie getStatus(){
        return status;
    }

    public String format(int idx){
        return String.format(
                Locale.US,
                "[%d] id=%d data=%s tip=%s suma=%.2f RON status=%s", idx, id, data, tip, suma, status
        );
    }
}