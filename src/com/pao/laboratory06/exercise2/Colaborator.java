package com.pao.laboratory06.exercise2;
import java.util.Scanner;

public abstract class Colaborator implements IOperatiiCitireScriere{
    protected String nume;
    protected String prenume;
    protected double venitBrutLunar;
    protected TipColaborator tip;

    public Colaborator(){}

    public Colaborator(String nume, String prenume, double venitBrutLunar, TipColaborator tip){
        this.nume = nume;
        this.prenume = prenume;
        this.venitBrutLunar = venitBrutLunar;
        this.tip = tip;
    }

    public abstract double calculeazaVenitNetAnual();

    public String getNume(){
        return nume;
    }

    public String getPrenume(){
        return prenume;
    }

    public double getVenitBrutLunar(){
        return venitBrutLunar;
    }

    public TipColaborator getTip(){
        return tip;
    }

    @Override
    public void citeste(Scanner in){
        this.nume = in.next();
        this.prenume = in.next();
        this.venitBrutLunar = in.nextDouble();
    }

    @Override
    public void afiseaza(){
        System.out.println(this);
    }

    @Override
    public String toString(){
        return String.format("%s: %s %s, venit net anual: %.2f lei", tipContract(), nume, prenume, calculeazaVenitNetAnual());
    }
}