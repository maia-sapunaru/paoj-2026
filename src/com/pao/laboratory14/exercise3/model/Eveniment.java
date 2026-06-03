package com.pao.laboratory14.exercise3.model;

public class Eveniment {

    private final String nume;
    private final int oraStart;
    private final int oraFinal;

    public Eveniment(String nume, int oraStart, int oraFinal) {
        this.nume = nume;
        this.oraStart = oraStart;
        this.oraFinal = oraFinal;
    }

    public String getNume() {
        return nume;
    }

    public int getOraStart() {
        return oraStart;
    }

    public int getOraFinal() {
        return oraFinal;
    }
}