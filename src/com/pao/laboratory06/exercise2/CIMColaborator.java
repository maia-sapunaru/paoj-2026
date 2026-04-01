package com.pao.laboratory06.exercise2;
import java.util.Scanner;

public class CIMColaborator extends PersoanaFizica{
    private boolean bonus;

    public CIMColaborator(){
        super();
        this.tip = TipColaborator.CIM;
        this.bonus = false;
    }

    public CIMColaborator(String nume, String prenume, double venitBrutLunar, boolean bonus){
        super(nume, prenume, venitBrutLunar, TipColaborator.CIM);
        this.bonus = bonus;
    }

    @Override
    public void citeste(Scanner in){
        super.citeste(in);
        this.tip = TipColaborator.CIM;
        this.bonus = false;

        if (in.hasNext()){
            String bonusText = in.next();
            this.bonus = bonusText.equalsIgnoreCase("DA");
        }
    }

    @Override
    public String tipContract(){
        return "CIM";
    }

    @Override
    public boolean areBonus(){
        return bonus;
    }

    @Override
    public double calculeazaVenitNetAnual(){
        double net = venitBrutLunar * 12 * 0.55;
        if (bonus){
            net *= 1.10;
        }
        return net;
    }
}