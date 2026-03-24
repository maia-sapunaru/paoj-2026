package com.pao.laboratory05.angajati;

import java.util.Arrays;

public class AngajatService {
    private Angajat[] angajati = new Angajat[0];

    private AngajatService(){}

    private static class Holder{
        private static final AngajatService INSTANCE = new AngajatService();
    }

    public static AngajatService getInstance(){
        return Holder.INSTANCE;
    }

    public void addAngajat(Angajat a){
        Angajat[] ang= new Angajat[angajati.length + 1];
        System.arraycopy(angajati, 0, ang, 0, angajati.length);
        ang[ang.length - 1] = a;
        angajati = ang;

        System.out.println("Angajat adaugat: " + a.getNume());
    }

    public void printAll(){
        if(angajati.length == 0)
            System.out.println("Nu exitsa angajati");
        else {
            for (Angajat angajat : angajati) {
                System.out.println(angajat);
            }
        }
    }

    public void listBySalary(){
        if(angajati.length == 0)
            System.out.println("Nu exitsa angajati");
        else{
            Angajat[] copy = angajati.clone();
            Arrays.sort(copy);

            for(Angajat angajat : copy){
                System.out.println(angajat);
            }
        }
    }

    public void findDepartament(String numeDept){
        boolean gasit = false;
        for(Angajat angajat : angajati){
            if(angajat.getDepartament().nume().equalsIgnoreCase(numeDept)) {
                System.out.println(angajat);
                gasit = true;
            }
        }
        if(!gasit)
            System.out.println("Niciun angajat in departamentul: " + numeDept);
    }

}
