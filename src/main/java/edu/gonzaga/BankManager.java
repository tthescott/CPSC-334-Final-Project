package edu.gonzaga;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

class BankManager {
    public static ArrayList<Player> loadAccountHolders(String fileName) throws IOException {
        ArrayList<Player> accountHolders = new ArrayList<>();
        if(Files.exists(Paths.get(fileName))) {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line = null;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",");
                // tokens must contain 4 tokens per line: playerID, name, balance, gains
                if (tokens.length != 4) {
                    throw new RuntimeException("Invalid file format " + fileName);
                }
                accountHolders.add(new Player(Integer.parseInt(tokens[0]), tokens[1],
                        Double.parseDouble(tokens[2]), Double.parseDouble(tokens[3])));
            }
            sortPlayersByIDs(accountHolders);
        }
        return accountHolders;
    }

    public static void saveAccountHolders(String fileName, ArrayList<Player> accountHolders) throws IOException {
        FileWriter outputFile = new FileWriter(fileName);
        StringBuffer sb = new StringBuffer();
        sortPlayersByIDs(accountHolders);
        for (Player accountHolder : accountHolders) {
            sb.append(accountHolder.getPlayerID()).append(",")
                    .append(accountHolder.getName()).append(",")
                    .append(accountHolder.getBalance()).append(",")
                    .append(accountHolder.getGains()).append("\n");
        }
        outputFile.write(sb.toString());
        outputFile.flush();
        outputFile.close();
    }


    public static ArrayList<Player> sortPlayersByGains(ArrayList<Player> accountHolders) {
        ArrayList<Player> copy = new ArrayList<>(accountHolders);
        copy.sort(new PlayerGainsComparator());

        return copy;
    }

    private static void sortPlayersByIDs(ArrayList<Player> accountHolders) {
        accountHolders.sort(new PlayerIDComparator());
    }

    public static Double loadTotalMoneySpent(String fileName) throws IOException {
        Double totalMoneySpent = 0.0;
        if(Files.exists(Paths.get(fileName))) {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line = reader.readLine();
            if(line != null) {
                try {
                    totalMoneySpent = Double.parseDouble(line);
                } catch (NumberFormatException e) {
                    reader.close();
                    throw new RuntimeException("Invalid file format " + fileName);
                }
            }
            reader.close();
        }
        return totalMoneySpent;
    }

    public static void saveTotalMoneySpent(String fileName, Double totalMoneySpent) throws IOException {
        FileWriter outputFile = new FileWriter(fileName);
        outputFile.write(totalMoneySpent + "\n");
        outputFile.flush();
        outputFile.close();
    }
}
