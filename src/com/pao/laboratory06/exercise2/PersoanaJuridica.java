package com.pao.laboratory06.exercise2;

public abstract class PersoanaJuridica extends Colaborator{
    protected double cheltuieliLunare;

    public PersoanaJuridica() {
        super();
    }

    public PersoanaJuridica(String nume, String prenume, double venitBrutLunar, double cheltuieliLunare, TipColaborator tip){
        super(nume, prenume, venitBrutLunar, tip);
        this.cheltuieliLunare = cheltuieliLunare;
    }

    public double getCheltuieliLunare(){
        return cheltuieliLunare;
    }
}