package edu.gonzaga;

import java.awt.*;
import javax.swing.*;
import javax.imageio.*;
import java.io.*;
import java.awt.event.*;
import java.util.Random;

// the cash out panel
public class CashOutPanel extends javax.swing.JPanel {

    // components
    private JButton cashOutButton; // no
    private JButton keepGamblingButton; // yes
    private Image background; // the background image
    private Random rand; // for the cash out button

    // default constructor
    public CashOutPanel(PlinkoGame plinkoGame){

        // panel setup
        this.setLayout(null);

        // instantiate components
        rand = new Random();
        this.cashOutButton = new JButton("cash out");
        this.keepGamblingButton = new JButton("KEEP GAMBLING!!!");

        // set the background image
        try{
            background = ImageIO.read(new File("src/main/java/edu/gonzaga/media/jpg.jpg"));
        }catch(IOException e){
            System.out.println("Some ting wong with cash out panel's background image");
        }
        this.background = this.background.getScaledInstance(1100, 700, Image.SCALE_SMOOTH);

        // cash out button setup
        this.cashOutButton.setBounds(50, 580, 100, 50);
        this.cashOutButton.setFont(new Font(cashOutButton.getFont().getName(), Font.BOLD, 16));

        // keep gambling button setup
        this.keepGamblingButton.setBounds(880, 580, 190, 50);
        this.keepGamblingButton.setFont(new Font(keepGamblingButton.getFont().getName(), Font.BOLD, 16));

        // add action listeners 
        this.setupActionListeners(plinkoGame);

        // add both buttons
        this.add(cashOutButton);
        this.add(keepGamblingButton);
    }

    @Override // set the background image
    protected void paintComponent(Graphics g){

        super.paintComponent(g);
        g.drawImage(background, 0, 0, null);
    }

    // sets up callbacks for all components
    private void setupActionListeners(PlinkoGame plinkoGame){

        // cash out button
        this.cashOutButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e){ // mouse goes over the button

                // move button to random location on screen
                randomizeButtonLocation(cashOutButton);
            }
        });

        // keep gambling button
        this.keepGamblingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                // switch to the play panel
                plinkoGame.switchToPlayPanel();
            }
        });
    }

    // randomizes button location within the panel
    private void randomizeButtonLocation(JButton button){

        // pick random pixel coordinates within the panel and apply them to the button
        Integer xRand = rand.nextInt(971) + 10;
        Integer yRand = rand.nextInt(605) + 10;
        button.setBounds(xRand, yRand, 100, 50);
    }
}