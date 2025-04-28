package edu.gonzaga;

import javax.swing.*;
import java.awt.GridBagLayout;
import java.io.IOException;
import java.util.ArrayList;

// the game class holds the frame, panels, player, and the settings
public class PlinkoGame {
    
    // the frame, panels,
    public JFrame frame;
    private IntroPanel introPanel;
    private PlayPanel playPanel;
    private CashOutPanel cashOutPanel;
    private StatsPanel statsPanel;
    private watchAdPanel watchAdPanel;
    private PlayerCreationPanel playerCreationPanel;

    // player
    public ArrayList<Player> accountHolders;
    public Player currentPLayer;
    public Boolean returningPlayer = false;

    // and the settings
    private Boolean traceSetting;
    private Boolean rainbowBall;
    private Double totalMoneySpent = 0.0;

    // default constructor
    public PlinkoGame() throws IOException {

        // frame setup
        this.frame = new JFrame("Plinko");
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setSize(1100, 700);
        this.frame.setResizable(false);

        // instantiate each panel (needed here, not just in panel switching methods fsr)
        this.playerCreationPanel = new PlayerCreationPanel(this);
        this.introPanel = new IntroPanel(this);
        this.cashOutPanel = new CashOutPanel(this);
        this.watchAdPanel = new watchAdPanel(this);

        // instantiate the settings
        this.traceSetting = false;
        this.rainbowBall = false;

        // load the playerList.csv and totalMoneySpent.csv
        accountHolders = BankManager.loadAccountHolders("playerList.csv");
        totalMoneySpent = BankManager.loadTotalMoneySpent("totalMoneySpent.csv");
    }

    // shows panels based on user input
    public void play(){

        // add the intro panel to the frame
        this.switchToIntroPanel();
        
        // display the frame
        this.frame.setVisible(true);
    }

    // turns the trace setting to true
    public void traceOn(){
        this.traceSetting = true;
    }

    // turns the trace setting to false
    public void traceOff(){
        this.traceSetting = false;
    }

    // gets the trace setting
    public Boolean traceSettingOn() {
        return this.traceSetting;
    }

    // set rainbowBall to true
    public void setRainbowBallOn(){
        this.rainbowBall = true;
    }

    // set rainbowBall to false
    public void setRainbowBallOff(){
        this.rainbowBall = false;
    }

    // is rainbow ball selected
    public Boolean rainbowBallOn(){
        return this.rainbowBall;
    }

    public void addToTotalMoneySpent(Double balance) {
        this.totalMoneySpent += balance;
    }

    public Double getTotalMoneySpent() {
        return totalMoneySpent;
    }

    //////// panel switching methods ////////

    // switches to the play panel
    public void switchToPlayPanel(){

        this.playPanel = new PlayPanel(this); // refreshes the panel
        this.playPanel.setLayout(new GridBagLayout()); // TODO: should be done in playPanel class, doesn't work there fsr
        this.switchPanel(this.playPanel); // switch to the panel
        this.playPanel.runPhysics(); // starts the physics sim
    }

    // switches to the intro panel
    public void switchToIntroPanel(){
        // when we start the game for the first time, player panel is not initialized yet
        if(playPanel != null) {
            this.playPanel.stopPhysics(); // this fixes the physics sim crashing issue
        }
        this.introPanel = new IntroPanel(this); // see comments in switchToPlayPanel() for rest of panel switching methods
        this.switchPanel(this.introPanel);
    }

    // switches to the intro panel
    public void switchToPlayerCreationPanel() {

        // when we start the game for the first time, player panel is not initialized yet
        if(playPanel != null) {
            this.playPanel.stopPhysics(); // this fixes the physics sim crashing issue
        }
        this.playerCreationPanel = new PlayerCreationPanel(this); // see comments in switchToPlayPanel() for rest of
        this.switchPanel(this.playerCreationPanel);
    }

    // switches to the cash out panel
    public void switchToWatchAdPanel() {

        // when we start the game for the first time, player panel is not initialized yet
        if(playPanel != null) {
            this.playPanel.stopPhysics(); // this fixes the physics sim crashing issue
        }
        this.watchAdPanel = new watchAdPanel(this);
        this.switchPanel(this.watchAdPanel);
    }

    // switches to the cash out panel
    public void switchToCashOutPanel(){

        // when we start the game for the first time, player panel is not initialized yet
        if(playPanel != null) {
            this.playPanel.stopPhysics(); // this fixes the physics sim crashing issue
        }
        this.cashOutPanel = new CashOutPanel(this);
        this.switchPanel(this.cashOutPanel);
    }

    // switches to the stats panel
    public void switchToStatsPanel(){

        // when we start the game for the first time, player panel is not initialized yet
        if(playPanel != null) {
            this.playPanel.stopPhysics(); // this fixes the physics sim crashing issue
        }
        this.statsPanel = new StatsPanel(this);
        this.switchPanel(this.statsPanel);
    }

    // switches the panel using setContentPane(), invalidate(), and validate()
    private void switchPanel(JPanel panel){ // TODO: erase previous panel?

        this.traceSetting = false;
        this.frame.setContentPane(panel); // changes the panel
        this.frame.invalidate(); // need this fsr
        this.frame.validate(); // also need this
    }

    public PlayPanel getPlayPanel() {
        return playPanel;
    }
}