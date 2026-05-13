package com.pao.laboratory11.exercise1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class Main {
    private static final Comparator<Transaction> BY_RISK_DESC_THEN_ID_ASC =
            Comparator.comparingInt(RiskScorer::riskScore)
                    .reversed()
                    .thenComparingInt(Transaction::getId);

    public static void main(String[] args) {
        try {
            run();
        } catch (IOException e) {
            System.out.println("ERR IO");
        }
    }

    private static void run() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String firstLine = readNonEmptyLine(br);
        if (firstLine == null) {
            return;
        }

        int n = Integer.parseInt(firstLine);

        List<Transaction> transactions = new ArrayList<>();
        Map<Integer, Transaction> byId = new HashMap<>();

        for (int i = 0; i < n; i++) {
            String line = readNonEmptyLine(br);
            if (line == null) {
                return;
            }

            Transaction tx = parseTransaction(line);
            transactions.add(tx);
            byId.put(tx.getId(), tx);
        }

        String qLine = readNonEmptyLine(br);
        if (qLine == null) {
            return;
        }

        int q = Integer.parseInt(qLine);

        Consumer<String> output = System.out::println;

        for (int i = 0; i < q; i++) {
            String commandLine = readNonEmptyLine(br);
            if (commandLine == null) {
                return;
            }

            executeCommand(commandLine, transactions, byId, output);
        }
    }

    private static void executeCommand(
            String commandLine,
            List<Transaction> transactions,
            Map<Integer, Transaction> byId,
            Consumer<String> output
    ) {
        String[] tokens = commandLine.split("\\s+");
        String command = tokens[0].toUpperCase();

        switch (command) {
            case "CHECK":
                if (tokens.length != 2) {
                    output.accept("ERR UNKNOWN_COMMAND");
                    return;
                }

                int id = Integer.parseInt(tokens[1]);
                Transaction tx = byId.get(id);

                if (tx == null) {
                    output.accept("CHECK " + id + " => NOT_FOUND");
                } else {
                    output.accept(TransactionFormatter.formatCheck(tx));
                }
                break;

            case "LIST_FLAGGED":
                if (tokens.length != 1) {
                    output.accept("ERR UNKNOWN_COMMAND");
                    return;
                }

                List<Transaction> flagged = transactions.stream()
                        .filter(RiskScorer.FLAGGED_RULE)
                        .sorted(BY_RISK_DESC_THEN_ID_ASC)
                        .toList();

                if (flagged.isEmpty()) {
                    output.accept("NONE");
                } else {
                    flagged.stream()
                            .map(TransactionFormatter::formatRiskLine)
                            .forEach(output);
                }
                break;

            case "TOP_RISK":
                if (tokens.length != 2) {
                    output.accept("ERR UNKNOWN_COMMAND");
                    return;
                }

                int k = Integer.parseInt(tokens[1]);

                transactions.stream()
                        .sorted(BY_RISK_DESC_THEN_ID_ASC)
                        .limit(Math.max(0, k))
                        .map(TransactionFormatter::formatRiskLine)
                        .forEach(output);
                break;

            default:
                output.accept("ERR UNKNOWN_COMMAND");
        }
    }

    private static Transaction parseTransaction(String line) {
        String[] tokens = line.split("\\s+");

        return new Transaction(
                Integer.parseInt(tokens[0]),
                Double.parseDouble(tokens[1]),
                tokens[2],
                tokens[3].toUpperCase(),
                tokens[4].toUpperCase()
        );
    }

    private static String readNonEmptyLine(BufferedReader br) throws IOException {
        String line;

        while ((line = br.readLine()) != null) {
            if (!line.trim().isEmpty()) {
                return line.trim();
            }
        }

        return null;
    }
}