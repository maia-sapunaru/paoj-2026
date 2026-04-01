package com.pao.laboratory06.exercise2;
import java.util.Scanner;

public class PFAColaborator extends PersoanaFizica{
    private static final double SALARIU_MINIM_BRUT_LUNAR = 4050.0;
    private static final double SALARIU_MINIM_BRUT_ANUAL = SALARIU_MINIM_BRUT_LUNAR * 12.0;

    private double cheltuieliLunare;

    public PFAColaborator(){
        super();
        this.tip = TipColaborator.PFA;
    }

    public PFAColaborator(String nume, String prenume, double venitBrutLunar, double cheltuieliLunare){
        super(nume, prenume, venitBrutLunar, TipColaborator.PFA);
        this.cheltuieliLunare = cheltuieliLunare;
    }

    @Override
    public void citeste(Scanner in){
        super.citeste(in);
        this.tip = TipColaborator.PFA;
        this.cheltuieliLunare = in.nextDouble();
    }

    @Override
    public String tipContract(){
        return "PFA";
    }

    @Override
    public double calculeazaVenitNetAnual(){
        double venitNet = (venitBrutLunar - cheltuieliLunare) * 12.0;

        double impozit = 0.10 * venitNet;

        double cass;
        if (venitNet < 6 * SALARIU_MINIM_BRUT_ANUAL){
            cass = 0.10 * (6 * SALARIU_MINIM_BRUT_ANUAL);
        }
        else if (venitNet <= 72 * SALARIU_MINIM_BRUT_ANUAL){
            cass = 0.10 * venitNet;
        }
        else
            cass = 0.10 * (72 * SALARIU_MINIM_BRUT_ANUAL);

        double cas;
        if (venitNet < 12 * SALARIU_MINIM_BRUT_ANUAL){
            cas = 0.0;
        }
        else if (venitNet <= 24 * SALARIU_MINIM_BRUT_ANUAL){
            cas = 0.25 * (12 * SALARIU_MINIM_BRUT_ANUAL);
        }
        else
            cas = 0.25 * (24 * SALARIU_MINIM_BRUT_ANUAL);

        return venitNet - impozit - cass - cas;
    }
}