package com.pao.laboratory11.exercise2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            run();
        } catch (IOException e) {
            // Output determinist pentru checker.
        }
    }

    private static void run() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String firstLine = nextNonEmpty(br);
        if (firstLine == null) {
            return;
        }

        int n = Integer.parseInt(firstLine);
        List<ReportTransaction> transactions = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            String line = nextNonEmpty(br);
            if (line == null) {
                return;
            }

            transactions.add(parseTransaction(line));
        }

        String qLine = nextNonEmpty(br);
        if (qLine == null) {
            return;
        }

        int q = Integer.parseInt(qLine);

        for (int i = 0; i < q; i++) {
            String commandLine = nextNonEmpty(br);
            if (commandLine == null) {
                return;
            }

            executeCommand(commandLine, transactions);
        }
    }

    private static void executeCommand(String commandLine, List<ReportTransaction> transactions) {
        String[] tokens = commandLine.split("\\s+");
        String command = tokens[0];

        switch (command) {
            case "REPORT_MONTH":
                ReportService.reportMonth(transactions, tokens[1]);
                break;

            case "REPORT_ACCOUNT":
                ReportService.reportAccount(transactions, tokens[1]);
                break;

            case "TOP_CHANNELS":
                ReportService.topChannels(transactions, Integer.parseInt(tokens[1]));
                break;

            default:
                // Cerinta pentru checker: comenzile necunoscute sunt ignorate.
                break;
        }
    }

    private static ReportTransaction parseTransaction(String line) {
        String[] tokens = line.split("\\s+");

        return new ReportTransaction(
                Integer.parseInt(tokens[0]),
                Double.parseDouble(tokens[1]),
                tokens[2],
                tokens[3],
                tokens[4],
                tokens[5]
        );
    }

    private static String nextNonEmpty(BufferedReader br) throws IOException {
        String line;

        while ((line = br.readLine()) != null) {
            if (!line.trim().isEmpty()) {
                return line.trim();
            }
        }

        return null;
    }
}