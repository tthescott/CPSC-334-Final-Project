package edu.gonzaga;

import java.awt.*;
import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PlayerCreationPanel extends javax.swing.JPanel {
    
    // panels to make design easier
    private JPanel namePanel;
    private JPanel playGamePanel;

    // buttons and text areas
    private JLabel enterName; 
    private JTextArea textName;
    private JButton playGame;

    // default constructor
    public PlayerCreationPanel(PlinkoGame plinkoGame) {

        // set layout
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        // create the panels 
        this.namePanel = new JPanel();
        this.playGamePanel = new JPanel();

        // instantiate components
        this.playGame = new JButton("Play Game");
        this.enterName = new JLabel("Enter Your Name: ");
        this.textName = new JTextArea(1,10);

        // sizing of panels 
        this.setMaximumSize(new Dimension(1100,700));
        this.namePanel.setMaximumSize(new Dimension(1100,50));
        this.playGamePanel.setMaximumSize(new Dimension(1100,50));

        // sizing of buttons for uniformity
        this.playGame.setMaximumSize(new Dimension(65, 30));
    
        // set font size for labels
        // this is goofy but first way I found that works
        Font labelFont = enterName.getFont();
        this.enterName.setFont(labelFont.deriveFont(Font.BOLD, 24f));
        
        // set font size for textAreas
        Font textAreaFont = new Font("Arial", Font.BOLD, 24);
        textName.setFont(textAreaFont);

        // set font size for buttons
        // this is goofy but first way I found that works
        Font buttonFont = playGame.getFont();
        this.playGame.setFont(buttonFont.deriveFont(Font.BOLD, 24f));

        // add the components
        //this.add(Box.createVerticalStrut(120));
        this.namePanel.add(enterName);
        this.namePanel.add(textName);
        this.playGamePanel.add(playGame);


        // make labels, text, buttons visible
        enterName.setVisible(true);
        textName.setVisible(true);
        playGame.setVisible(true);

        // add other panels to this panel
        this.add(Box.createVerticalStrut(150));
        this.add(namePanel);
        this.add(playGamePanel);

        // make panels visible 
        this.namePanel.setVisible(true);
        this.playGamePanel.setVisible(true);

        // setup the action listeners
        this.setupActionListeners(plinkoGame);
    }


    private void setupActionListeners(PlinkoGame plinkoGame) {
        // create player and start game
        this.playGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String playerEnteredName = textName.getText();
                plinkoGame.returningPlayer = false;

                for(Player player : plinkoGame.accountHolders) {
                    if(playerEnteredName.equals(player.getName())) {
                        // set current player status
                        plinkoGame.currentPLayer = player;
                        plinkoGame.returningPlayer = true;
                        break;
                    }
                }

                if(!plinkoGame.returningPlayer) {
                    int newPlayerID = plinkoGame.accountHolders.size(); // size of list is always 1+ than existing IDs
                    if(playerEnteredName.isEmpty()) {
                        playerEnteredName = "No Named Dude " + newPlayerID;
                    }
                    plinkoGame.currentPLayer = new Player(newPlayerID, playerEnteredName);
                    plinkoGame.accountHolders.add(plinkoGame.currentPLayer);
                }
//                plinkoGame.getPlayPanel().getPlayerNameLabel().setText("Name: " + plinkoGame.currentPLayer.getName());
//                plinkoGame.getPlayPanel().getPlayerBankLabel().setText("Bank Account: " + plinkoGame.currentPLayer.getBalance());

                plinkoGame.switchToPlayPanel();
            }
        });
    }
}
