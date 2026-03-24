package com.pao.laboratory05.audit;


import java.util.Scanner;

/**
 * Exercise 4 (Bonus) — Audit Log
 *
 * Cerințele complete se află în:
 *   src/com/pao/laboratory05/Readme.md  →  secțiunea "Exercise 4 (Bonus) — Audit"
 *
 * Extinde soluția de la Exercise 3 cu un sistem de audit bazat pe record.
 * Creează fișierele de la zero în acest pachet, apoi rulează Main.java
 * pentru a verifica output-ul așteptat din Readme.
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("Cerințele se află în Readme.md — secțiunea Exercise 3.");

        Scanner scanner = new Scanner(System.in);
        AngajatService serv = AngajatService.getInstance();

        while (true) {
            System.out.println("\n===== Gestionare Angajați =====");
            System.out.println("1. Adaugă angajat");
            System.out.println("2. Listare după salariu");
            System.out.println("3. Caută după departament");
            System.out.println("4. Afiseaza audit log");
            System.out.println("0. Ieșire");
            System.out.print("Opțiune: ");

            int optiune;


            optiune = scanner.nextInt();
            scanner.nextLine();

            switch(optiune){
                case 1:
                    System.out.println("Nume angajat: ");
                    String nume = scanner.nextLine();

                    System.out.println("Nume departament: ");
                    String numeDepartament = scanner.nextLine();

                    System.out.println("Locatie departament: ");
                    String locatieDepartament = scanner.nextLine();

                    System.out.println("Salariu: ");
                    double salariu;

                    if(scanner.hasNextDouble()){
                        salariu = scanner.nextDouble();
                        scanner.nextLine();
                    }
                    else{
                        System.out.println("Valoare invalida pentru salariu");
                        scanner.nextLine();
                        break;
                    }

                    Departament departament = new Departament(numeDepartament, locatieDepartament);
                    Angajat angajat = new Angajat(nume, departament, salariu);
                    serv.addAngajat(angajat);
                    break;

                case 2:
                    System.out.println("--- Angajati in ordine descrescatoare, dupa salariu ---");
                    serv.listBySalary();
                    break;

                case 3:
                    System.out.println("Introdu numele departamentului: ");
                    String numeDepart = scanner.nextLine();

                    System.out.println("--- Rezultat ---");
                    serv.findDepartament(numeDepart);
                    break;

                case 4:
                    serv.printAuditLog();
                    break;
                case 0:
                    System.out.println("La revedere!");
                    return;

                default:
                    System.out.println("Optiune invalida!");
            }


        }
    }

}