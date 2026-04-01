package com.pao.laboratory06.exercise2;
import java.util.Scanner;

public class SRLColaborator extends PersoanaJuridica{

    public SRLColaborator(){
        super();
        this.tip = TipColaborator.SRL;
    }

    public SRLColaborator(String nume, String prenume, double venitBrutLunar, double cheltuieliLunare){
        super(nume, prenume, venitBrutLunar, cheltuieliLunare, TipColaborator.SRL);
    }

    @Override
    public void citeste(Scanner in){
        super.citeste(in);
        this.tip = TipColaborator.SRL;
        this.cheltuieliLunare = in.nextDouble();
    }

    @Override
    public String tipContract(){
        return "SRL";
    }

    @Override
    public double calculeazaVenitNetAnual(){
        return (venitBrutLunar - cheltuieliLunare) * 12.0 * 0.84;
    }
}