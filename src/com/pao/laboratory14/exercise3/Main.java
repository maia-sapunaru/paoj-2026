package com.pao.laboratory14.exercise3;

import com.pao.laboratory14.exercise3.model.Eveniment;
import com.pao.laboratory14.exercise3.service.PlanificatorSali;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        List<Eveniment> evenimente = List.of(
                new Eveniment("Concert A", 10, 12),
                new Eveniment("Conferinta B", 11, 13),
                new Eveniment("Spectacol C", 12, 14),
                new Eveniment("Workshop D", 13, 15),
                new Eveniment("Gala E", 14, 16)
        );

        PlanificatorSali planificator = new PlanificatorSali();

        int minimSali = planificator
                .calculeazaNumarMinimSali(evenimente);

        System.out.println(
                "Numar minim de sali necesare: " + minimSali
        );
    }
}