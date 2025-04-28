package edu.gonzaga;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

// the stats panel that opens when the player presses PlayPanel::statsButton
public class StatsPanel extends javax.swing.JPanel {

    // components
    private JLabel totalMoneySpent = new JLabel();
    private JButton backButton = new JButton(); // goes back to IntroPanel
    private PlinkoGame plinkoGame;


    // default constructor
    public StatsPanel(PlinkoGame plinkoGame){
        // save plinkoGame
        this.plinkoGame = plinkoGame;
        // set layout
        this.setLayout(new BorderLayout());

        // add components
        this.add(BorderLayout.NORTH, getStatsHeaderPanel());
        this.add(BorderLayout.CENTER, getPlayerPlacementsPanel());
        this.add(BorderLayout.SOUTH, getTotalMoneySpentPanel());

        this.setupActionListeners(plinkoGame);
    }


    public JPanel getStatsHeaderPanel() {
        JPanel statsHeader = new JPanel();
        statsHeader.setLayout(new BorderLayout());
        statsHeader.setMaximumSize(new Dimension(2000, 200));
        statsHeader.setPreferredSize(new Dimension(1800, 110));

        // stats label
        JPanel statsLabelPanel = new JPanel();
        statsLabelPanel.setLayout(new BorderLayout());
        JLabel statsLabel = new JLabel("STATS");
        statsLabel.setFont(new Font("Serif", Font.BOLD, 70));
        statsLabelPanel.setMaximumSize(new Dimension(750, 200));
        statsLabelPanel.setPreferredSize(new Dimension(650, 110));
        statsLabelPanel.add(BorderLayout.EAST, statsLabel);

        // back to intro button
        JPanel backButtonPanel = new JPanel();
        backButtonPanel.setLayout(new FlowLayout());
        this.backButton.setFocusable(false);    // removes text outline from inside the button
        this.backButton.setText("Back to Intro");
        this.backButton.setMaximumSize(new Dimension(200,100));
        this.backButton.setPreferredSize(new Dimension(180,70));
        this.backButton.setFont(new Font("Serif", Font.BOLD, 25));
        backButtonPanel.add(this.backButton);
        backButtonPanel.setMaximumSize(new Dimension(300, 200));
        backButtonPanel.setPreferredSize(new Dimension(220, 110));

        // add statsLabelPanel and backButtonPanel to the header panel
        statsHeader.add(BorderLayout.WEST, statsLabelPanel);
        statsHeader.add(BorderLayout.EAST, backButtonPanel);

        return statsHeader;
    }


    private JPanel getPlayerPlacementsPanel() {
        JPanel playerPlacements = new JPanel();
        playerPlacements.setBorder(BorderFactory.createLineBorder(Color.black));
        playerPlacements.setLayout(new GridLayout(1,2));

        playerPlacements.add(getHighestWinnersPanel());
        playerPlacements.add(getBiggestLosersPanel());

        return playerPlacements;
    }


    private JPanel getHighestWinnersPanel() {
        JPanel highestWinners = new JPanel();
        highestWinners.setLayout(new BorderLayout());

        highestWinners.add(BorderLayout.NORTH, getHighestWinnersHeaderPanel());
        highestWinners.add(BorderLayout.CENTER, getHighestWinnersListPanel());

        return highestWinners;
    }


    private JPanel getHighestWinnersHeaderPanel() {
        JPanel highestWinnersHeader = new JPanel();
        highestWinnersHeader.setLayout(new FlowLayout());

        JLabel highestWinnersLabel = new JLabel("Highest Winners:");
        highestWinnersLabel.setFont(new Font("Serif", Font.BOLD, 35));
        highestWinnersHeader.setMaximumSize(new Dimension(500, 500));
        highestWinnersHeader.setPreferredSize(new Dimension(300, 50));

        highestWinnersHeader.add(highestWinnersLabel);

        return  highestWinnersHeader;
    }

    private JPanel getHighestWinnersListPanel() {
        JPanel highestWinnersListPanel = new JPanel();
        highestWinnersListPanel.setLayout(new BorderLayout());

        // header that contains the labels for player name and total winnings
        JPanel listHeaderPanel = new JPanel();
        listHeaderPanel.setLayout(new BorderLayout());
        listHeaderPanel.setMaximumSize(new Dimension(500, 50));
        listHeaderPanel.setPreferredSize(new Dimension(300, 42));

        JPanel playerNameLabelHolder = new JPanel();
        playerNameLabelHolder.setLayout(new BorderLayout());
        playerNameLabelHolder.setMaximumSize(new Dimension(500, 100));
        playerNameLabelHolder.setPreferredSize(new Dimension(230, 100));

        JPanel playerTotalWinningsHolder = new JPanel();
        playerTotalWinningsHolder.setLayout(new FlowLayout());

        JLabel playerNameLabel = new JLabel("<HTML><U>Player Name</U></HTML>");
        JLabel playerTotalWinnings = new JLabel("<HTML><U>Player Total Winnings</U></HTML>");
        playerNameLabel.setFont(new Font("Serif", Font.PLAIN, 24));
        playerTotalWinnings.setFont(new Font("Serif", Font.PLAIN, 24));

        playerNameLabelHolder.add(BorderLayout.EAST, playerNameLabel);
        playerTotalWinningsHolder.add(playerTotalWinnings);

        listHeaderPanel.add(BorderLayout.WEST, playerNameLabelHolder);
        listHeaderPanel.add(playerTotalWinningsHolder);

        // player list that contains the player names and their total winnings
        JPanel playerListPanel = new JPanel();
        playerListPanel.setLayout(new BoxLayout(playerListPanel, BoxLayout.Y_AXIS));
        ArrayList<Player> accountHolders = BankManager.sortPlayersByGains(plinkoGame.accountHolders);
        for(Player player : accountHolders) {
            JPanel playerHolder = new JPanel();
            playerHolder.setLayout(new GridLayout(1,2));
            playerHolder.setMaximumSize(new Dimension(400,50));
            playerHolder.setPreferredSize(new Dimension(50,50));

            JPanel leftHolder = new JPanel();
            leftHolder.setLayout(new FlowLayout());

            JPanel rightHolder = new JPanel();
            rightHolder.setLayout(new FlowLayout());

            JLabel playerName = new JLabel(player.getName());
            JLabel playerWinnings = new JLabel(String.valueOf(player.getGains()));
            playerName.setFont(new Font("Serif", Font.PLAIN, 20));
            playerWinnings.setFont(new Font("Serif", Font.PLAIN, 20));

            rightHolder.add(playerName);
            leftHolder.add(playerWinnings);

            playerHolder.add(rightHolder);
            playerHolder.add(leftHolder);

            playerListPanel.add(playerHolder);
        }

        // scroll pane that makes playerListPanel scrollable
        JScrollPane playerListPane = new JScrollPane(playerListPanel);

        // add listHeaderPanel and playerListPane to highestWinnersListPanel
        highestWinnersListPanel.add(BorderLayout.NORTH, listHeaderPanel);
        highestWinnersListPanel.add(playerListPane);

        return highestWinnersListPanel;
    }

    private JPanel getBiggestLosersPanel() {
        JPanel biggestLosers = new JPanel();
        biggestLosers.setLayout(new BorderLayout());

        biggestLosers.add(BorderLayout.NORTH, getBiggestLosersHeaderPanel());
        biggestLosers.add(BorderLayout.CENTER, getBiggestLosersListPanel());

        return biggestLosers;
    }

    private JPanel getBiggestLosersHeaderPanel() {
        JPanel biggestLosersHeader = new JPanel();
        biggestLosersHeader.setLayout(new FlowLayout());

        JLabel biggestLosersLabel = new JLabel("Biggest Losers:");
        biggestLosersLabel.setFont(new Font("Serif", Font.BOLD, 35));
        biggestLosersHeader.setMaximumSize(new Dimension(500, 500));
        biggestLosersHeader.setPreferredSize(new Dimension(300, 50));

        biggestLosersHeader.add(biggestLosersLabel);

        return biggestLosersHeader;
    }

    private JPanel getBiggestLosersListPanel() {
        JPanel biggestLosersListPanel = new JPanel();
        biggestLosersListPanel.setLayout(new BorderLayout());

        // header that contains the labels for player name and total winnings
        JPanel listHeaderPanel = new JPanel();
        listHeaderPanel.setLayout(new BorderLayout());
        listHeaderPanel.setMaximumSize(new Dimension(500, 50));
        listHeaderPanel.setPreferredSize(new Dimension(300, 42));

        JPanel playerNameLabelHolder = new JPanel();
        playerNameLabelHolder.setLayout(new BorderLayout());
        playerNameLabelHolder.setMaximumSize(new Dimension(500, 100));
        playerNameLabelHolder.setPreferredSize(new Dimension(230, 100));

        JPanel playerTotalWinningsHolder = new JPanel();
        playerTotalWinningsHolder.setLayout(new FlowLayout());

        JLabel playerNameLabel = new JLabel("<HTML><U>Player Name</U></HTML>");
        JLabel playerTotalWinnings = new JLabel("<HTML><U>Player Total Lossings</U></HTML>");
        playerNameLabel.setFont(new Font("Serif", Font.PLAIN, 24));
        playerTotalWinnings.setFont(new Font("Serif", Font.PLAIN, 24));

        playerNameLabelHolder.add(BorderLayout.EAST, playerNameLabel);
        playerTotalWinningsHolder.add(playerTotalWinnings);

        listHeaderPanel.add(BorderLayout.WEST, playerNameLabelHolder);
        listHeaderPanel.add(playerTotalWinningsHolder);

        // player list that contains the player names and their total winnings
        JPanel playerListPanel = new JPanel();
        playerListPanel.setLayout(new BoxLayout(playerListPanel, BoxLayout.Y_AXIS));
        ArrayList<Player> accountHolders = BankManager.sortPlayersByGains(plinkoGame.accountHolders);
        Collections.reverse(accountHolders);
        for(Player player : accountHolders) {
            JPanel playerHolder = new JPanel();
            playerHolder.setLayout(new GridLayout(1,2));
            playerHolder.setMaximumSize(new Dimension(400,50));
            playerHolder.setPreferredSize(new Dimension(50,50));

            JPanel leftHolder = new JPanel();
            leftHolder.setLayout(new FlowLayout());

            JPanel rightHolder = new JPanel();
            rightHolder.setLayout(new FlowLayout());

            JLabel playerName = new JLabel(player.getName());
            JLabel playerWinnings = new JLabel(String.valueOf(player.getGains()));
            playerName.setFont(new Font("Serif", Font.PLAIN, 20));
            playerWinnings.setFont(new Font("Serif", Font.PLAIN, 20));

            rightHolder.add(playerName);
            leftHolder.add(playerWinnings);

            playerHolder.add(rightHolder);
            playerHolder.add(leftHolder);

            playerListPanel.add(playerHolder);
        }

        // scroll pane that makes playerListPanel scrollable
        JScrollPane playerListPane = new JScrollPane(playerListPanel);

        // add listHeaderPanel and playerListPane to biggestLosersListPanel
        biggestLosersListPanel.add(BorderLayout.NORTH, listHeaderPanel);
        biggestLosersListPanel.add(playerListPane);

        return biggestLosersListPanel;
    }

    private JPanel getTotalMoneySpentPanel() {
        JPanel totalMoneySpent = new JPanel();
        totalMoneySpent.setLayout(new FlowLayout());

        JLabel totalMoneySpentLabel = new JLabel("Total Money Spent:");
        this.totalMoneySpent.setText(String.valueOf(plinkoGame.getTotalMoneySpent()));
        totalMoneySpentLabel.setFont(new Font("Serif", Font.BOLD, 30));
        this.totalMoneySpent.setFont(new Font("Serif", Font.BOLD, 30));
        totalMoneySpent.setMaximumSize(new Dimension(2000, 500));
        totalMoneySpent.setPreferredSize(new Dimension(1800, 60));

        totalMoneySpent.add(totalMoneySpentLabel);
        totalMoneySpent.add(this.totalMoneySpent);

        return totalMoneySpent;
    }

    // sets up all the action listeners
    private void setupActionListeners(PlinkoGame plinkoGame) {

        // back button
        this.backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // switch to the intro panel
                plinkoGame.switchToIntroPanel();
            }
        });
    }
}