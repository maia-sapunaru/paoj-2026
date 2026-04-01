package com.pao.laboratory06.exercise3;

public class Inginer extends Angajat implements PlataOnline, Comparable<Inginer> {
    private String username;
    private String parola;
    private double sold;

    public Inginer(String nume, String prenume, String telefon, double salariu, String username, String parola, double sold){
        super(nume, prenume, telefon, salariu);

        if (username == null || username.isBlank() || parola == null || parola.isBlank()){
            throw new IllegalArgumentException("Username si parola nu pot fi nule sau goale");
        }
        if (sold < 0){
            throw new IllegalArgumentException("Soldul nu poate fi negativ");
        }

        this.username = username;
        this.parola = parola;
        this.sold = sold;
    }

    @Override
    public void autentificare(String user, String parola) {
        if (user == null || user.isBlank() || parola == null || parola.isBlank()) {
            throw new IllegalArgumentException("User sau parola invalide.");
        }

        if (this.username.equals(user) && this.parola.equals(parola)) {
            System.out.println("Autentificare reușită pentru inginerul " + this);
        } else {
            System.out.println("Autentificare eșuată pentru inginerul " + this);
        }
    }

    @Override
    public double consultareSold() {
        return sold;
    }

    @Override
    public boolean efectuarePlata(double suma) {
        if (suma <= 0) {
            throw new IllegalArgumentException("Suma trebuie să fie pozitivă.");
        }

        if (sold >= suma) {
            sold -= suma;
            return true;
        }

        return false;
    }

    @Override
    public int compareTo(Inginer altInginer) {
        return this.nume.compareToIgnoreCase(altInginer.nume);
    }

    @Override
    public String toString() {
        return "Inginer{nume='" + nume + "', prenume='" + prenume + "', salariu=" + salariu + ", sold=" + sold + "}";
    }
}