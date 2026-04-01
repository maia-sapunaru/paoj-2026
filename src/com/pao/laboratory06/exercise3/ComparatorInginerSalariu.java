package com.pao.laboratory06.exercise3;

import java.util.Comparator;

public class ComparatorInginerSalariu implements Comparator<Inginer>{
    @Override
    public int compare(Inginer ing1, Inginer ing2){
        return Double.compare(ing2.getSalariu(), ing1.getSalariu());
    }
}