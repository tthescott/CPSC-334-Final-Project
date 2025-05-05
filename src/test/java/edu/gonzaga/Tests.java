package edu.gonzaga;

import java.awt.*;
import java.util.Random;
import javax.swing.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Tests {
    @Test
    void setButtonSize() {
        JButton button = new JButton();
        button.setMaximumSize(new Dimension(150, 60));

        Dimension test = button.getMaximumSize();
        Dimension expect = new Dimension(150, 60);

        Assertions.assertEquals(test, expect);
    }

    @Test
    void setButtonFont() {
        JButton button = new JButton();
        button.setFont(button.getFont().deriveFont(Font.BOLD, 24f));

        float testsi = button.getFont().getSize();
        float expectsi = 24f;

        int testst = button.getFont().getStyle();
        int expectst = Font.BOLD;

        Assertions.assertEquals(testsi, expectsi);
        Assertions.assertEquals(testst, expectst);
    }

    @Test
    void setButtonLocation() {
        JButton button = new JButton();
        button.setAlignmentX(Component.CENTER_ALIGNMENT);

        float test = button.getAlignmentX();
        float expect = Component.CENTER_ALIGNMENT;

        Assertions.assertEquals(test, expect);
    }

    @Test
    void cashOutPanelRandomize() {
        JButton button = new JButton();
        Random rand = new Random();
        Integer xRand = rand.nextInt(971) + 10;
        Integer yRand = rand.nextInt(605) + 10;
        button.setBounds(xRand, yRand, 100, 50);

        Boolean pass = true;
        if (button.getBounds().x > 971 || button.getBounds().x < 10) {
            pass = false;
        }
        if (button.getBounds().y > 605 || button.getBounds().y < 10) {
            pass = false;
        }
        Assertions.assertTrue(pass);
    }

    @Test
    void setPlayerStats() {
        Player p = new Player(1, "scott", 100.0, 0.0);
        Assertions.assertEquals(p.getPlayerID(), 1);
        Assertions.assertEquals(p.getName(), "scott");
        Assertions.assertEquals(p.getBalance(), 100.0);
        Assertions.assertEquals(p.getGains(), 0.0);
    }

    @Test
    void setPlayerName() {
        Player p = new Player(1, "scott", 100.0, 0.0);
        p.setName("jack");
        Assertions.assertEquals(p.getName(), "jack");
    }

    @Test
    void creditPlayer() {
        Player p = new Player(1, "scott", 100.0, 0.0);
        p.credit(203.4);
        Assertions.assertEquals(p.getBalance(), 303.4);
    }

    @Test
    void debitPlayer() {
        Player p = new Player(1, "scott", 100.0, 0.0);
        p.debit(50.3);
        Assertions.assertEquals(p.getBalance(), 49.7);
    }

    @Test
    void debitPlayerTooLow() {
        Player p = new Player(1, "scott", 100.0, 0.0);
        Assertions.assertThrows(InsufficientFundsException.class, () -> p.debit(203.4));
    }

    @Test
    void watchAd() {
        Player p = new Player(1, "scott", 100.0, 0.0);
        p.watchAdRevenue();
        Assertions.assertEquals(p.getBalance(), 125.0);
    }
}
