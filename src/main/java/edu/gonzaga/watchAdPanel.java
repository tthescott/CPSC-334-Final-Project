package edu.gonzaga;

import java.awt.*;
import javax.swing.*;
import javax.imageio.*;
import java.io.*;
import java.awt.event.*;
import java.util.Random;

// the cash out panel
public class watchAdPanel extends javax.swing.JPanel {

    // components
    private JButton goBack;
    private Image background; // the background image

    // default constructor
    public watchAdPanel(PlinkoGame plinkoGame){

        // panel setup
        this.setLayout(null);

        // instantiate components
        this.goBack = new JButton("X");
        
        // set up button 
        this.goBack.setBounds(25, 25, 50, 50);
        this.goBack.setFont(new Font(goBack.getFont().getName(), Font.BOLD, 25));
        goBack.setVisible(false);
        this.add(goBack);

        // Get a random number between 1 and 10 for images that will be ads
        Random rand = new Random();
        Integer imageNum = rand.nextInt(6)+1;

        // set the background image
        try{
            if (imageNum.equals(6)){
                this.background = ImageIO.read(new File("src/main/java/edu/gonzaga/media/" + imageNum.toString() + ".gif"));
            }else{
                this.background = ImageIO.read(new File("src/main/java/edu/gonzaga/media/" + imageNum.toString() + ".jpg"));
            }
        }catch(IOException e){
            System.out.println("watch ad image error");
        }
        this.background = this.background.getScaledInstance(1100, 700, Image.SCALE_SMOOTH);

        // add action listeners 
        this.setupActionListeners(plinkoGame);

        timer.start();
    }

    @Override // set the background image
    protected void paintComponent(Graphics g){

        super.paintComponent(g);
        g.drawImage(background, 0, 0, null);
    }

    // sets up callbacks for all components
    private void setupActionListeners(PlinkoGame plinkoGame){
        // keep gambling button
        this.goBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // switch to the play panel
                plinkoGame.currentPLayer.watchAdRevenue();
                plinkoGame.switchToPlayPanel();
            }
        });
    }

    Timer timer = new Timer(3500, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Toggle the visibility of the button after 5 seconds
            goBack.setVisible(true);
        }
    });
}