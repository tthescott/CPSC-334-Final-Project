package edu.gonzaga;

import java.awt.*;
import javax.swing.*;
import javax.imageio.*;
import java.io.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// the intro screen that includes various buttons and the difficulty select
public class IntroPanel extends javax.swing.JPanel {

    // components on the intro screen
    private JButton playButton; // goes to PlayPanel
    private JButton quitButton; // quits the entire game
    private JButton statsButton; // goes to StatsPanel
    private Image background; // the background image

    // default constructor
    public IntroPanel(PlinkoGame plinkoGame) {

        // panel setup
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        // instantiate components
        this.playButton = new JButton("Play");
        this.quitButton = new JButton("Quit");
        this.statsButton = new JButton("Stats");

        // set the background image
        try {
            background = ImageIO.read(new File("src/main/java/edu/gonzaga/media/plinko.jpg"));
        } catch (IOException e) {
            System.out.println("Some ting wong with cash out panel's background image");
        }
        this.background = this.background.getScaledInstance(1100, 700, Image.SCALE_SMOOTH);

        // sizing of buttons for uniformity
        this.playButton.setMaximumSize(new Dimension(150, 60));
        this.statsButton.setMaximumSize(new Dimension(150, 60));
        this.quitButton.setMaximumSize(new Dimension(150, 60));

        // set font size for buttons
        // this is goofy but first way I found that works
        Font buttonFont = playButton.getFont();
        this.playButton.setFont(buttonFont.deriveFont(Font.BOLD, 24f));
        this.statsButton.setFont(buttonFont.deriveFont(Font.BOLD, 24f));
        this.quitButton.setFont(buttonFont.deriveFont(Font.BOLD, 24f));

        // set button location
        this.playButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.statsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.quitButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // add the components
        this.add(Box.createVerticalStrut(250));
        this.add(playButton);
        this.add(Box.createVerticalStrut(5));
        this.add(statsButton);
        this.add(Box.createVerticalStrut(5));
        this.add(quitButton);

        // setup the action listeners
        this.setupActionListeners(plinkoGame);
    }

    // sets up each component's action listener
    private void setupActionListeners(PlinkoGame plinkoGame) {

        // play button
        this.playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // switch to the play panel
                plinkoGame.switchToPlayerCreationPanel();
            }
        });

        // stats button
        this.statsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // switch to the stats panel
                plinkoGame.switchToStatsPanel();
            }
        });

        // quit button
        this.quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // saves total money spent
                try {
                    BankManager.saveTotalMoneySpent("totalMoneySpent.csv", plinkoGame.getTotalMoneySpent());
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

                // ends the program, exits the frame, kills it, die, no
                System.exit(0);
            }
        });
    }
    
    @Override // set the background image
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);
        g.drawImage(background, 0, 0, null);
    }
}