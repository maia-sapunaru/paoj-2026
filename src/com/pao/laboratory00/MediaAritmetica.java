package com.pao.laboratory00;

import java.util.Scanner;

/**
 * Exercitiul 1
 *
 * Cititi de la tastatura un sir cu n elemente intregi.
 *
 * 1. Afisati elementele sirului in doua modalitati.
 * 2. Afisati media aritmetica a elementelor sirului.
 *
 */

public class MediaAritmetica {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] array = new int[n];

        for (int i = 0; i < n; i++)
            array[i] = scanner.nextInt();

        for (int num : array)
            System.out.println(num);

        for (int i = 0; i < array.length; i++)
            System.out.println(array[i]);

        int suma = 0;
        for (int num : array)
            suma += num;

        double media = (double) suma / n;
        System.out.println(media);
    }
}