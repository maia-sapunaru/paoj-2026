package com.pao.laboratory08.exercise2;

import com.pao.laboratory08.exercise1.Adresa;
import com.pao.laboratory08.exercise1.Student;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main{
    private static final String FILE_PATH = "src/com/pao/laboratory08/tests/studenti.txt";

    public static void main(String[] args) throws Exception{
        List<Student> studenti = citesteStudentiDinFisier(FILE_PATH);

        Scanner scanner = new Scanner(System.in);
        int prag = scanner.nextInt();

        List<Student> filtrati = new ArrayList<>();
        for (Student student : studenti){
            if (student.getVarsta() >= prag){
                filtrati.add(student);
            }
        }

        BufferedWriter fout = new BufferedWriter(new FileWriter("rezultate.txt"));
        for (Student student : filtrati){
            fout.write(student.toString());
            fout.newLine();
        }
        fout.close();

        System.out.println("Filtru: varsta >= " + prag);
        System.out.println("Rezultate: " + filtrati.size() + " studenti");
        System.out.println();

        for (Student student : filtrati){
            System.out.println(student);
        }

        System.out.println();
        System.out.println("Scris in: rezultate.txt");
    }

    private static List<Student> citesteStudentiDinFisier(String filePath) throws Exception{
        List<Student> studenti = new ArrayList<>();

        BufferedReader fin = new BufferedReader(new FileReader(filePath));
        String linie;

        while ((linie = fin.readLine()) != null){
            if (linie.trim().isEmpty()){
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

        fin.close();
        return studenti;
    }
}