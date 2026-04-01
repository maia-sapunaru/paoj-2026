package com.pao.laboratory06.exercise2;

public abstract class PersoanaFizica extends Colaborator{
    public PersoanaFizica(){
        super();
    }
    public PersoanaFizica(String nume, String prenume, double VenitBrutLunar, TipColaborator tip){
        super(nume, prenume, VenitBrutLunar, tip);
    }
}
