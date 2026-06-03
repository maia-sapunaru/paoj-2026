package com.pao.laboratory14.exercise3.service;

import com.pao.laboratory14.exercise3.model.Eveniment;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class PlanificatorSali {

    public int calculeazaNumarMinimSali(List<Eveniment> evenimente) {

        if (evenimente == null || evenimente.isEmpty()) {
            return 0;
        }

        List<Eveniment> sortate = new ArrayList<>(evenimente);

        sortate.sort(
                Comparator.comparingInt(Eveniment::getOraStart)
                        .thenComparingInt(Eveniment::getOraFinal)
        );
        PriorityQueue<Integer> saliOcupate = new PriorityQueue<>();

        int maximSali = 0;

        for (Eveniment eveniment : sortate) {
            while (
                    !saliOcupate.isEmpty()
                            && saliOcupate.peek() <= eveniment.getOraStart()
            ) {
                saliOcupate.poll();
            }
            saliOcupate.offer(eveniment.getOraFinal());

            maximSali = Math.max(
                    maximSali,
                    saliOcupate.size()
            );
        }

        return maximSali;
    }
}