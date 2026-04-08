package com.pao.laboratory07.exercise2;

import com.pao.laboratory07.exercise1.OrderState;

public abstract sealed class Comanda
        permits ComandaStandard, ComandaRedusa, ComandaGratuita{

    protected String nume;
    protected OrderState stareInitiala;

    public Comanda(String nume){
        this.nume = nume;
        this.stareInitiala = OrderState.PLACED;
    }

    public String getNume(){
        return nume;
    }

    public OrderState getStareInitiala(){
        return stareInitiala;
    }

    public abstract double pretFinal();

    public abstract String descriere();
}