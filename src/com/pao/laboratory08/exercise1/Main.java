package com.pao.laboratory08.exercise1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static final String FILE_PATH = "src/com/pao/laboratory08/tests/studenti.txt";

    public static void main(String[] args) throws Exception {
        List<Student> studenti = citesteStudentiDinFisier(FILE_PATH);

        BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
        String comanda = consoleReader.readLine();

        if (comanda == null || comanda.trim().isEmpty()) {
            return;
        }

        String[] tokens = comanda.trim().split("\\s+", 2);
        String actiune = tokens[0];

        if ("PRINT".equals(actiune)) {
            for (Student student : studenti) {
                System.out.println(student);
            }
        } else if ("SHALLOW".equals(actiune) && tokens.length == 2) {
            String nume = tokens[1].trim();
            Student original = cautaStudentDupaNume(studenti, nume);

            if (original != null) {
                Student clona = original.shallowClone();
                clona.getAdresa().setOras("MODIFICAT");

                System.out.println("Original: " + original);
                System.out.println("Clona: " + clona);
            }
        } else if ("DEEP".equals(actiune) && tokens.length == 2) {
            String nume = tokens[1].trim();
            Student original = cautaStudentDupaNume(studenti, nume);

            if (original != null) {
                Student clona = original.deepClone();
                clona.getAdresa().setOras("MODIFICAT");

                System.out.println("Original: " + original);
                System.out.println("Clona: " + clona);
            }
        }
    }

    private static List<Student> citesteStudentiDinFisier(String filePath) throws Exception {
        List<Student> studenti = new ArrayList<>();

        BufferedReader fileReader = new BufferedReader(new FileReader(filePath));
        String linie;

        while ((linie = fileReader.readLine()) != null) {
            if (linie.trim().isEmpty()) {
                continue;
            }

            String[] parti = linie.split(",", 4);
            String nume = parti[0].trim();
            int varsta = Integer.parseInt(parti[1].trim());
            String oras = parti[2].trim();
            String strada = parti[3].trim();

            Adresa adresa = new Adresa(oras, strada);
            Student student = new Student(nume, varsta, adresa);
            studenti.add(student);
        }

        fileReader.close();
        return studenti;
    }

    private static Student cautaStudentDupaNume(List<Student> studenti, String nume) {
        for (Student student : studenti) {
            if (student.getNume().equals(nume)) {
                return student;
            }
        }
        return null;
    }
}