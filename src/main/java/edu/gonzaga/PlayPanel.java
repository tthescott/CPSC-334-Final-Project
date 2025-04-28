package edu.gonzaga;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridBagConstraints;
import javax.swing.*;
import java.awt.GridBagLayout;
import java.io.IOException;

import static edu.gonzaga.BankManager.saveAccountHolders;

// the main screen of the game that contains the physics simulation and other components
public class PlayPanel extends javax.swing.JPanel {
    
    // the panel that contains the plinko physics simulation
    private PhysicsPanel physicsPanel;
    private JPanel featuresPanel;

    // other components
    private JLabel playerNameLabel;
    private JLabel playerBankLabel;
    private JLabel betAmountLabel;
    private JTextField betAmountField; // need to pass into each ball
    private JButton sendBallButton;
    private JButton watchAdButton; // add another panel to show an ad? (ad will cover entire screen)
    private JButton cashOutButton; // goes to CashOutPanel
    private JButton introButton; // goes back to IntroPanel
    private JLabel ballColorLabel;
    private JComboBox<String> ballColorBox; // will have "random" option
    private JCheckBox traceCheckBox;
    private JLabel traceLabel;

    // default constructor
    public PlayPanel(PlinkoGame plinkoGame){

        // setup panel
        this.setLayout(new GridBagLayout());

        // instantiate components
        this.playerNameLabel = new JLabel();
        this.playerBankLabel = new JLabel();
        this.betAmountLabel = new JLabel();
        this.betAmountField = new JTextField();
        this.sendBallButton = new JButton();
        this.watchAdButton = new JButton();
        this.cashOutButton = new JButton();
        this.introButton = new JButton();
        this.ballColorLabel = new JLabel();
        this.ballColorBox = new JComboBox<String>(); // Currently can't change color
        this.traceCheckBox = new JCheckBox();
        this.traceLabel = new JLabel();

        // create new panels to go inside the play panel
        this.physicsPanel = new PhysicsPanel(plinkoGame);
        this.featuresPanel = new JPanel();
        
        // set panel sizes 
        this.featuresPanel.setPreferredSize(new Dimension(150,600));
        this.featuresPanel.setMaximumSize(new Dimension(150,600));
        this.physicsPanel.setSize(new Dimension(800,600));
        this.physicsPanel.setMaximumSize(new Dimension(800,600));
        
        // set panel layouts
        // used GridBag layout for this panel
        // used flowLayout for features panel
        this.featuresPanel.setLayout(new FlowLayout());
        GridBagConstraints c = new GridBagConstraints();

        //part of gridbag layout
        c.weightx = .5;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        this.add(physicsPanel, c); // add physics panel to playPanel

        //part of gridbag layout
        c.weightx = .5;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 0;
        this.add(featuresPanel, c); // add features panel to playPanel

        // add all components to panel 
        featuresPanel.add(playerNameLabel);
        featuresPanel.add(playerBankLabel);
        featuresPanel.add(betAmountLabel);
        featuresPanel.add(betAmountField);
        featuresPanel.add(sendBallButton);
        featuresPanel.add(watchAdButton);
        featuresPanel.add(cashOutButton);
        featuresPanel.add(introButton);
        featuresPanel.add(ballColorLabel);
        featuresPanel.add(ballColorBox);
        featuresPanel.add(traceLabel);
        featuresPanel.add(traceCheckBox);

        //make all components visible
        this.playerNameLabel.setVisible(true);
        this.playerBankLabel.setVisible(true);
        this.betAmountLabel.setVisible(true);
        this.betAmountField.setVisible(true);
        this.sendBallButton.setVisible(true);
        this.watchAdButton.setVisible(true);
        this.cashOutButton.setVisible(true);
        this.introButton.setVisible(true);
        this.ballColorLabel.setVisible(true);
        this.ballColorBox.setVisible(true);
        this.traceCheckBox.setVisible(true);
        this.traceLabel.setVisible(true);

        //set the text of labels and buttons and 
        this.playerNameLabel.setText("Name: " + plinkoGame.currentPLayer.getName());
        this.playerBankLabel.setText("Bank Account: " + plinkoGame.currentPLayer.getBalance());
        this.betAmountLabel.setText("Bet Amount: ");
        this.sendBallButton.setText("Send Ball");
        this.watchAdButton.setText("Watch Ad");
        this.cashOutButton.setText("Cash Out");
        this.introButton.setText("Return to Intro");
        this.ballColorLabel.setText("Select Ball Color:");
        this.traceLabel.setText("Trace Mode:");

        // add items to combo box
        this.ballColorBox.addItem("Red");
        this.ballColorBox.addItem("Oragne");
        this.ballColorBox.addItem("Yellow");
        this.ballColorBox.addItem("Green");
        this.ballColorBox.addItem("Blue");
        this.ballColorBox.addItem("Magenta");
        this.ballColorBox.addItem("Black");
        this.ballColorBox.addItem("Rainbow");

        // set sizes of labels, text fields, and buttons
        this.betAmountField.setPreferredSize(new Dimension(50,16));
        this.playerBankLabel.setMaximumSize(new Dimension(20,20));
        this.cashOutButton.setMaximumSize(new Dimension(20,20));

        //add action listeners
        this.setupActionListeners(plinkoGame);

        // make physics panel visible
        this.physicsPanel.setVisible(true);
        this.featuresPanel.setVisible(true);
    }

    // sets up action listeners
    private void setupActionListeners(PlinkoGame plinkoGame){

        // send ball button
        this.sendBallButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                // send a ball
                Double balance = 0.0;
                Color color = Color.WHITE;

                try{
                    balance = Double.parseDouble(betAmountField.getText());
                    plinkoGame.currentPLayer.recordWinnings(-balance);
                    plinkoGame.addToTotalMoneySpent(balance);
                    playerBankLabel.setText("Bank Account: " + plinkoGame.currentPLayer.getBalance());
                }catch(Exception w){
                    balance = 0.0;
                }

                // get color from combo box
                String colorString = ballColorBox.getSelectedItem().toString();

                //if rainbow is selected will put boolean to true
                // have ball change color every time it hits spoke
                if(colorString.toLowerCase().equals("rainbow")){
                    plinkoGame.setRainbowBallOn();
                    physicsPanel.sendBall(balance);
                }else{
                    // set ball color to what was selected in combobox
                    plinkoGame.setRainbowBallOff();
                    color = getBallColor(color, colorString);
                    physicsPanel.sendBall(color, balance);
                }
            }

            // used to match combobox data to color of ball
            private Color getBallColor(Color color, String colorStr) {
                switch(colorStr.toLowerCase()){
                    case"red":
                    color = Color.RED;
                    break;
                    case"oragne":
                    color = Color.ORANGE;
                    break;
                    case"yellow":
                    color = Color.YELLOW;
                    break;
                    case"green":
                    color = Color.GREEN;
                    break;
                    case"blue":
                    color = Color.BLUE;
                    break;
                    case"magenta":
                    color = Color.MAGENTA;
                    break;
                    case"black":
                    color = Color.BLACK;
                    break;
                }
                return color;
            }
        });

        // watch ad button
        this.watchAdButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // switch to cash out panel if there's no balls in play
                if (physicsPanel.getBallCtr().equals(0)) {
                    plinkoGame.switchToWatchAdPanel();
                }
            }
        });

        // cash out button
        this.cashOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){

                // switch to cash out panel if there's no balls in play
                if (physicsPanel.getBallCtr().equals(0)){
                    plinkoGame.switchToCashOutPanel();
                }
            }
        });

        // intro button
        this.introButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){

                // save player data to file
                try {
                    saveAccountHolders("playerList.csv", plinkoGame.accountHolders);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

                // switch to intro panel if there's no balls in play
                if (physicsPanel.getBallCtr().equals(0)){
//                    plinkoGame.currentPLayer.recordWinnings(physicsPanel.getWinnings());
                    plinkoGame.switchToIntroPanel();
                }
            }
        });

        // trace check box
        this.traceCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){

                if (traceCheckBox.isSelected()){
                    plinkoGame.traceOn(); // turn trace setting on if box is selected
                }else{
                    plinkoGame.traceOff(); // off when it is unselected
                }
            }
        });
    }

    // runs the physics sim
    public void runPhysics(){

        this.physicsPanel.run();
    }

    // stops the physics sim (prevents physics sim from crashing on panel change)
    public void stopPhysics(){
        
        this.physicsPanel.stop();
    }

    public JLabel getPlayerNameLabel() {
        return playerNameLabel;
    }

    public JLabel getPlayerBankLabel() {
        return playerBankLabel;
    }
}