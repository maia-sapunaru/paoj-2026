package com.pao.laboratory05.biblioteca;
import java.util.Arrays;
import java.util.Comparator;


public class BibliotecaService {
    private BibliotecaService(){}

    private static class Holder {
        private static final BibliotecaService INSTANCE = new BibliotecaService();
    }

    public static BibliotecaService getInstance() {
        return Holder.INSTANCE;
    }

    private Carte[] carti = new Carte[0];

    public void addCarte(Carte carte){
        Carte[] c = new Carte[carti.length + 1];
        System.arraycopy(carti, 0, c, 0, carti.length);
        c[c.length - 1] = carte;
        carti = c;

        System.out.println("Carte adaugata: " + carte.getTitlu());
    }


    public void listSortedByRating(){
        Carte[] copy = carti.clone();
        Arrays.sort(copy);
        for(Carte carte : copy){
            System.out.println(carte);
        }
    }

    public void listSortedBy(Comparator<Carte> comparator){
        Carte[] copy = carti.clone();
        Arrays.sort(copy, comparator);
        for(Carte carte : copy){
            System.out.println(carte);
        }
    }


}
