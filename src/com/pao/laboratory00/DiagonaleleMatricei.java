package com.pao.laboratory00;
import java.util.Scanner;

/**
 * Exercitiul 2
 *
 * Cititi de la tastatura o matrice de n ori n elemente REALE.
 *
 * 1. Afisati matricea in consola.
 * 2. Afisati suma elementelor de pe diagonala principala
 *    si produsul elementelor de pe diagonala secundara.
 *
 */
public class DiagonaleleMatricei {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        double[][] mat = new double[n][n];
        for(int i = 0; i < n; i++)
            for(int j = 0; j < n;j++)
                mat[i][j] = scanner.nextDouble();

        for(int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++)
                System.out.print(mat[i][j] + " ");
            System.out.println();
        }

        double sumaDiagPrinc = 0;
        double prodDiagSec = 1;

        for(int i = 0; i < n; i++) {
            sumaDiagPrinc += mat[i][i];
            prodDiagSec *= mat[i][n-1-i];
        }

        System.out.println(sumaDiagPrinc);
        System.out.println(prodDiagSec);
    }
}
