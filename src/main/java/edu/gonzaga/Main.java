/*
 * Plinko main driver class
 * 
 * 
 * Project Description: Plinko is a beautiful game where you get to gamble. You start with money,
 *                      then end with even more money, all the time. You pick how much money you
 *                      want to bet, send a ball down, and are returned that bet multiplied by the
 *                      multiplier on whatever bucket it falls into. The program implements Java Swing,
 *                      Java AWT, and the Dyn4j physics engine.
 * 
 * 
 * Contributors: Jack Kabil, Mark Sanghera, Scott Riddle
 * 
 * 
 * Copyright: 2024
 * 
 * 
 * Disclaimer: The contributors of this project are not the original authors of the Camera, Graphics2DRenderer,
 *             SimulationBody, or SimulationPanel classes. However, the contributors of this project have made changes to
 *             these classes, and thus have included them amongst the source code. All classes not authored by the 
 *             contributors of this project have been attributed to their original authors in the top comments of the class.
 * 
 */

package edu.gonzaga;

import java.io.IOException;

/** Main program class for launching your team's program. */
public class Main {
    public static void main(String[] args) throws IOException {
        // make a new plinko game and play it
        PlinkoGame plinkoGame = new PlinkoGame();
        plinkoGame.play();
    }
}
