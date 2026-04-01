package com.pao.laboratory06.exercise3;

import java.util.ArrayList;
import java.util.List;

public class PersoanaJuridica extends Persoana implements PlataOnlineSMS {
    private List<String> smsTrimise;
    private String username;
    private String parola;
    private double sold;

    public PersoanaJuridica(String nume, String prenume, String telefon, String username, String parola, double sold){
        super(nume, prenume, telefon);

        if (username == null || username.isBlank() || parola == null || parola.isBlank()){
            throw new IllegalArgumentException("Username și parola nu pot fi nule sau goale.");
        }

        if (sold < 0){
            throw new IllegalArgumentException("Soldul nu poate fi negativ.");
        }

        this.username = username;
        this.parola = parola;
        this.sold = sold;
        this.smsTrimise = new ArrayList<>();
    }

    @Override
    public void autentificare(String user, String parola) {
        if (user == null || user.isBlank() || parola == null || parola.isBlank()){
            throw new IllegalArgumentException("User sau parola invalide.");
        }

        if (this.username.equals(user) && this.parola.equals(parola)){
            System.out.println("Autentificare reușită pentru persoana juridică " + this);
        }
        else
            System.out.println("Autentificare eșuată pentru persoana juridică " + this);
    }

    @Override
    public double consultareSold(){
        return sold;
    }

    @Override
    public boolean efectuarePlata(double suma){
        if (suma <= 0){
            throw new IllegalArgumentException("Suma trebuie să fie pozitivă.");
        }

        if (sold >= suma){
            sold -= suma;
            return true;
        }

        return false;
    }

    @Override
    public boolean trimiteSMS(String mesaj){
        if (mesaj == null || mesaj.isBlank()){
            return false;
        }

        if (telefon == null || telefon.isBlank()){
            return false;
        }

        smsTrimise.add(mesaj);
        return true;
    }

    public List<String> getSmsTrimise(){
        return smsTrimise;
    }

    @Override
    public String toString(){
        return "PersoanaJuridica{nume='" + nume + "', prenume='" + prenume + "', sold=" + sold + "}";
    }
}