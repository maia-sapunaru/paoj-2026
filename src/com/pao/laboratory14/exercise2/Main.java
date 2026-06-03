package com.pao.laboratory14.exercise2;

import com.pao.laboratory14.exercise1.TipBilet;
import com.pao.laboratory14.exercise2.model.Eveniment;
import com.pao.laboratory14.exercise2.repository.EvenimentRepository;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        try {

            EvenimentRepository repository = new EvenimentRepository();

            Scanner scanner = new Scanner(System.in);

            while (scanner.hasNext()) {

                String comanda = scanner.next();

                switch (comanda) {

                    case "ADD" -> {

                        String nume = scanner.next();
                        String data = scanner.next();
                        int capacitate = scanner.nextInt();
                        TipBilet tip = TipBilet.valueOf(scanner.next());

                        Eveniment eveniment = new Eveniment(
                                nume,
                                data,
                                capacitate,
                                tip
                        );

                        repository.save(eveniment);

                        System.out.println(
                                "Adaugat: [" +
                                        eveniment.getId() +
                                        "] " +
                                        eveniment.getNume()
                        );
                    }

                    case "LIST" -> {

                        List<Eveniment> evenimente = repository.findAll();

                        for (Eveniment e : evenimente) {

                            System.out.println(
                                    "[" + e.getId() + "] " +
                                            e.getNume() +
                                            " | " +
                                            e.getData() +
                                            " | cap=" +
                                            e.getCapacitate() +
                                            " | " +
                                            e.getTip()
                            );
                        }
                    }

                    case "DELETE" -> {

                        int id = scanner.nextInt();

                        int deletedRows = repository.deleteImpl(id);

                        if (deletedRows > 0) {
                            System.out.println("Sters: " + id);
                        } else {
                            System.out.println("Nu exista: " + id);
                        }
                    }

                    case "COUNT" -> {
                        System.out.println(
                                "Total: " + repository.count()
                        );
                    }
                }
            }

        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}