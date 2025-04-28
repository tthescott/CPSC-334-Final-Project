package edu.gonzaga;

//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class TestStatsPanel {

    PlinkoGame plinkoGame = new PlinkoGame();

    JFrame mainWindowFrame;
    TestStatsPanel() throws IOException {
        mainWindowFrame = new JFrame("Test Stats Panel");
        mainWindowFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindowFrame.setLocation(100,100);
    }
//    void testHighestWinners() {
//        StatsPanel statsPanel = new StatsPanel(plinkoGame);
//        mainWindowFrame.getContentPane().add(BorderLayout.NORTH, statsPanel.getStatsHeaderPanel());
//        mainWindowFrame.getContentPane().add(BorderLayout.CENTER, statsPanel.get());
//        mainWindowFrame.pack();
//        mainWindowFrame.setVisible(true);
//    }

    public static void main(String [] args) throws IOException {
        TestStatsPanel app = new TestStatsPanel();    // Create, then run GUI
//        app.testHighestWinners();
    }
}
