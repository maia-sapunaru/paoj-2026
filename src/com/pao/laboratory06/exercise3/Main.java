package com.pao.laboratory06.exercise3;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Inginer ing1 = new Inginer("Popescu", "Ana", "0711111111", 8000, "ana.pop", "pass1", 5000);
        Inginer ing2 = new Inginer("Ionescu", "Vlad", "0722222222", 12000, "vlad.ion", "pass2", 7000);
        Inginer ing3 = new Inginer("Georgescu", "Maria", "0733333333", 10000, "maria.geo", "pass3", 6500);

        Inginer[] ingineri = {ing1, ing2, ing3};

        System.out.println("Ingineri inainte de sortare");
        for (Inginer ing : ingineri){
            System.out.println(ing);
        }

        Arrays.sort(ingineri);
        System.out.println("Ingineri sortati natural (dupa nume)");
        for (Inginer ing : ingineri){
            System.out.println(ing);
        }

        Arrays.sort(ingineri, new ComparatorInginerSalariu());
        System.out.println("Ingineri sortați dupa salariu descrescator");
        for (Inginer ing : ingineri){
            System.out.println(ing);
        }

        PlataOnline plataOnline = ing1;
        plataOnline.autentificare("ana.pop", "pass1");
        System.out.println("Sold curent: " + plataOnline.consultareSold());
        System.out.println("Plata 1200 lei: " + plataOnline.efectuarePlata(1200));
        System.out.println("Sold după plată: " + plataOnline.consultareSold());

        PlataOnlineSMS firma1 = new PersoanaJuridica("Tech", "Solutions", "0744444444", "tech.user", "firma1", 20000);
        PlataOnlineSMS firma2 = new PersoanaJuridica("NoPhone", "Company", "", "nophone.user", "firma2", 15000);

        System.out.println("Persoana juridica si SMS");
        firma1.autentificare("tech.user", "firma1");
        System.out.println("Sold firma 1: " + firma1.consultareSold());
        System.out.println("SMS valid trimis? " + firma1.trimiteSMS("Plata a fost procesata."));
        System.out.println("SMS gol trimis? " + firma1.trimiteSMS(""));

        System.out.println("SMS fara telefon trimis? " + firma2.trimiteSMS("Mesaj important."));

        PersoanaJuridica pj1 = (PersoanaJuridica) firma1;
        System.out.println("SMS-uri stocate pentru firma1: " + pj1.getSmsTrimise());

        PersoanaJuridica pj2 = (PersoanaJuridica) firma2;
        System.out.println("SMS-uri stocate pentru firma2: " + pj2.getSmsTrimise());

        // 6. Afișare constantă din enum
        System.out.println("Constante financiare");
        System.out.println("TVA = " + ConstanteFinanciare.TVA.getValoare());
        System.out.println("SALARIU_MINIM = " + ConstanteFinanciare.SALARIU_MINIM.getValoare());

        // 7. Demonstrarea cazurilor de eroare
        System.out.println("Tratare erori");

        try {
            plataOnline.autentificare(null, "1234");
        } catch (IllegalArgumentException e) {
            System.out.println("Eroare autentificare cu user null: " + e.getMessage());
        }

        try {
            plataOnline.efectuarePlata(-100);
        } catch (IllegalArgumentException e) {
            System.out.println("Eroare plată invalidă: " + e.getMessage());
        }

        try{
            Object obiect = ing2;
            PlataOnlineSMS smsInvalid = (PlataOnlineSMS) obiect;
            smsInvalid.trimiteSMS("Acest apel nu ar trebui samearga.");
        } catch (ClassCastException e){
            System.out.println("Eroare:  nu are capabilitate SMS.");
        }

        try{
            trimiteSMSDacaSePoate(ing3, "Mesaj pentru inginer");
        } catch (UnsupportedOperationException e){
            System.out.println("UnsupportedOperationException: " + e.getMessage());
        }
    }

    public static void trimiteSMSDacaSePoate(PlataOnline entitate, String mesaj){
        if (!(entitate instanceof PlataOnlineSMS)){
            throw new UnsupportedOperationException("Entitatea nu suporta trimiterea de SMS.");
        }

        PlataOnlineSMS entitateSMS = (PlataOnlineSMS) entitate;
        boolean rezultat = entitateSMS.trimiteSMS(mesaj);
        System.out.println("Rezultat trimitere SMS: " + rezultat);
    }
}