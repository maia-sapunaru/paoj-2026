package com.pao.laboratory07.exercise3;

import com.pao.laboratory07.exercise1.OrderState;

public abstract sealed class Comanda
        permits ComandaStandard, ComandaRedusa, ComandaGratuita{

    protected String nume;
    protected String client;
    protected OrderState stareInitiala;

    public Comanda(String nume, String client){
        this.nume = nume;
        this.client = client;
        this.stareInitiala = OrderState.PLACED;
    }

    public String getNume(){
        return nume;
    }

    public String getClient(){
        return client;
    }

    public OrderState getStareInitiala(){
        return stareInitiala;
    }

    public abstract double pretFinal();

    public abstract String tip();

    public abstract String descriere();

    public abstract String descriereScurta();
}