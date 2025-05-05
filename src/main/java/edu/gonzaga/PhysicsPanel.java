package edu.gonzaga;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import javax.imageio.*;
import org.dyn4j.collision.*;
import org.dyn4j.dynamics.*;
import org.dyn4j.geometry.*;
import org.dyn4j.world.*;
import org.dyn4j.world.listener.*;

// the panel that contains the plinko physics simulation (goes inside PlayPanel)
public class PhysicsPanel extends SimulationPanel {

    // Filter (loosely copied from dyn4j)
    private static final long ALL = Long.MAX_VALUE;
    private static final long OTHER = 4;
    private static final CategoryFilter allFilter = new CategoryFilter(OTHER, ALL);

    private Double winnings = 0.0;

    // buckets
    private SimulationBody left5x;
    private SimulationBody left3x;
    private SimulationBody left1x;
    private SimulationBody left08x;
    private SimulationBody left06x;
    private SimulationBody left04x;
    private SimulationBody middle02x;
    private SimulationBody right04x;
    private SimulationBody right06x;
    private SimulationBody right08x;
    private SimulationBody right1x;
    private SimulationBody right3x;
    private SimulationBody right5x;

    // walls
    private SimulationBody leftWall;
    private SimulationBody rightWall;
    private SimulationBody slotWallOne;
    private SimulationBody slotWallTwo;
    private SimulationBody slotWallThree;
    private SimulationBody slotWallFour;
    private SimulationBody slotWallFive;
    private SimulationBody slotWallSix;
    private SimulationBody slotWallSeven;
    private SimulationBody slotWallEight;
    private SimulationBody slotWallNine;
    private SimulationBody slotWallTen;
    private SimulationBody slotWallEleven;
    private SimulationBody slotWallTwelve;

    // spokes
    private ArrayList<SimulationBody> spokes = new ArrayList<SimulationBody>(12);

    // bucket labels
    private ImageBody left5xLabel;
    private ImageBody left3xLabel;
    private ImageBody left1xLabel;
    private ImageBody left08xLabel;
    private ImageBody left06xLabel;
    private ImageBody left04xLabel;
    private ImageBody middle02xLabel;
    private ImageBody right04xLabel;
    private ImageBody right06xLabel;
    private ImageBody right08xLabel;
    private ImageBody right1xLabel;
    private ImageBody right3xLabel;
    private ImageBody right5xLabel;

    // bucket label images
    private Image image5x;
    private Image image3x;
    private Image image1x;
    private Image image08x;
    private Image image06x;
    private Image image04x;
    private Image image02x;

    // how many balls are falling
    private Integer ballCtr;

    // body fixture
    private BodyFixture bodyFix;

    // constructor
    public PhysicsPanel(PlinkoGame plinkoGame) {

        super("SimulationPanel");
        this.ballCtr = 0; // instantiate the ballCtr
        this.setupCollisionListener(plinkoGame);
    }

    // getter for the ballCtr
    public Integer getBallCtr() {

        return this.ballCtr;
    }

    // called by the physics engine in SimulationPanel
    protected void initializeWorld() {

        this.setupImages();
        this.creationOfWorldBodies();
        this.addBodiesToFixture();
        this.rotateWallsAndSlots();
        this.moveWorldBodies();

        bodyFix.setFilter(allFilter);

        this.setWorldBodiesMass();
        this.addWorldBodiesToWorld();
        this.setUpSpokes();
    }

    // read in the label images and assign them
    private void setupImages() {

        // read in files for each image
        try { // need try-catch for "unhandled IO exception"
            this.image5x =
                    ImageIO.read(getClass().getResourceAsStream("/edu/gonzaga/media/5x.png"));
            this.image3x =
                    ImageIO.read(getClass().getResourceAsStream("/edu/gonzaga/media/3x.png"));
            this.image1x =
                    ImageIO.read(getClass().getResourceAsStream("/edu/gonzaga/media/1x.png"));
            this.image08x =
                    ImageIO.read(getClass().getResourceAsStream("/edu/gonzaga/media/08x.png"));
            this.image06x =
                    ImageIO.read(getClass().getResourceAsStream("/edu/gonzaga/media/06x.png"));
            this.image04x =
                    ImageIO.read(getClass().getResourceAsStream("/edu/gonzaga/media/04x.png"));
            this.image02x =
                    ImageIO.read(getClass().getResourceAsStream("/edu/gonzaga/media/02x.png"));
        } catch (Exception e) {
            System.out.println("bucket label images broke :(");
        }
    }

    // sets up the collision listener, includes methods only used by this method
    private void setupCollisionListener(PlinkoGame plinkoGame) {

        // create a new CollisionListener of SimulationBodies and BodyFixtures
        CollisionListener<SimulationBody, BodyFixture> cl =
                new CollisionListenerAdapter<SimulationBody, BodyFixture>() {
                    @Override
                    public boolean collision(
                            NarrowphaseCollisionData<SimulationBody, BodyFixture> collision) {

                        SimulationBody ball = collision.getBody1(); // the ball
                        SimulationBody body = collision.getBody2(); // the other thing

                        // rainbow setting
                        if (plinkoGame.rainbowBallOn()) {
                            rainbowBall(ball);
                        }

                        // trace setting
                        if (plinkoGame.traceSettingOn()) {
                            checkHitSpokes(ball, body, spokes);
                        }

                        // ball colliding with the buckets
                        checkHitFloor(ball, body, left5x);
                        checkHitFloor(ball, body, left3x);
                        checkHitFloor(ball, body, left1x);
                        checkHitFloor(ball, body, left08x);
                        checkHitFloor(ball, body, left06x);
                        checkHitFloor(ball, body, left04x);
                        checkHitFloor(ball, body, middle02x);
                        checkHitFloor(ball, body, right04x);
                        checkHitFloor(ball, body, right06x);
                        checkHitFloor(ball, body, right08x);
                        checkHitFloor(ball, body, right1x);
                        checkHitFloor(ball, body, right3x);
                        checkHitFloor(ball, body, right5x);

                        return super.collision(collision);
                    }

                    // if the ball collides with the floor then the floor will change to the same
                    // color of the ball
                    private void checkHitFloor(
                            SimulationBody ball, SimulationBody body, SimulationBody floor) {
                        Double moneyReturned = 0.0;
                        if (body.equals(floor)) {
                            moneyReturned = ball.getBalance() * floor.getBalance();
                            world.removeBody(ball);
                            ballCtr--;

                            if (moneyReturned != 0) {
                                winnings += moneyReturned;
                                plinkoGame.currentPLayer.recordWinnings(moneyReturned);
                                plinkoGame
                                        .getPlayPanel()
                                        .getPlayerBankLabel()
                                        .setText(
                                                "Bank Account: "
                                                        + plinkoGame.currentPLayer.getBalance());
                            }
                        }
                    }

                    // if the ball collides with a spoke then the spoke will change to same color as
                    // the ball
                    private void checkHitSpokes(
                            SimulationBody ball,
                            SimulationBody body,
                            ArrayList<SimulationBody> spokes) {
                        for (SimulationBody spoke : spokes) {
                            if (body.equals(spoke)) {
                                body.setColor(ball.getColor());
                            }
                        }
                    }

                    // the ball will change color every time it hits a spoke
                    private void rainbowBall(SimulationBody ball) {
                        ball.setColor(Graphics2DRenderer.getRandomColor());
                    }
                };

        this.world.addCollisionListener(cl);
    }

    // instantiates all the bodies
    private void creationOfWorldBodies() {

        // Creation of buckets
        left5x = new SimulationBody(Color.BLACK, 5.0);
        left3x = new SimulationBody(Color.BLACK, 3.0);
        left1x = new SimulationBody(Color.BLACK, 1.0);
        left08x = new SimulationBody(Color.BLACK, 0.8);
        left06x = new SimulationBody(Color.BLACK, 0.6);
        left04x = new SimulationBody(Color.BLACK, 0.4);
        middle02x = new SimulationBody(Color.BLACK, 0.2);
        right04x = new SimulationBody(Color.BLACK, 0.4);
        right06x = new SimulationBody(Color.BLACK, 0.6);
        right08x = new SimulationBody(Color.BLACK, 0.8);
        right1x = new SimulationBody(Color.BLACK, 1.0);
        right3x = new SimulationBody(Color.BLACK, 3.0);
        right5x = new SimulationBody(Color.BLACK, 5.0);
        leftWall = new SimulationBody(Color.BLACK);
        rightWall = new SimulationBody(Color.BLACK);

        // Creation of slot walls
        slotWallOne = new SimulationBody(Color.BLACK);
        slotWallTwo = new SimulationBody(Color.BLACK);
        slotWallThree = new SimulationBody(Color.BLACK);
        slotWallFour = new SimulationBody(Color.BLACK);
        slotWallFive = new SimulationBody(Color.BLACK);
        slotWallSix = new SimulationBody(Color.BLACK);
        slotWallSeven = new SimulationBody(Color.BLACK);
        slotWallEight = new SimulationBody(Color.BLACK);
        slotWallNine = new SimulationBody(Color.BLACK);
        slotWallTen = new SimulationBody(Color.BLACK);
        slotWallEleven = new SimulationBody(Color.BLACK);
        slotWallTwelve = new SimulationBody(Color.BLACK);

        // creation of slot labels
        left5xLabel = new ImageBody(image5x);
        left3xLabel = new ImageBody(image3x);
        left1xLabel = new ImageBody(image1x);
        left08xLabel = new ImageBody(image08x);
        left06xLabel = new ImageBody(image06x);
        left04xLabel = new ImageBody(image04x);
        middle02xLabel = new ImageBody(image02x);
        right04xLabel = new ImageBody(image04x);
        right06xLabel = new ImageBody(image06x);
        right08xLabel = new ImageBody(image08x);
        right1xLabel = new ImageBody(image1x);
        right3xLabel = new ImageBody(image3x);
        right5xLabel = new ImageBody(image5x);
    }

    // gives the bodies fixtures (idk)
    private void addBodiesToFixture() {

        // Give barriers structure
        bodyFix = left5x.addFixture(Geometry.createRectangle(2.0, 1.0));
        bodyFix = left3x.addFixture(Geometry.createRectangle(2.0, 1.0));
        bodyFix = left1x.addFixture(Geometry.createRectangle(2.0, 1.0));
        bodyFix = left08x.addFixture(Geometry.createRectangle(2.0, 1.0));
        bodyFix = left06x.addFixture(Geometry.createRectangle(2.0, 1.0));
        bodyFix = left04x.addFixture(Geometry.createRectangle(2.0, 1.0));
        bodyFix = middle02x.addFixture(Geometry.createRectangle(2.0, 1.0));
        bodyFix = right04x.addFixture(Geometry.createRectangle(2.0, 1.0));
        bodyFix = right06x.addFixture(Geometry.createRectangle(2.0, 1.0));
        bodyFix = right08x.addFixture(Geometry.createRectangle(2.0, 1.0));
        bodyFix = right1x.addFixture(Geometry.createRectangle(2.0, 1.0));
        bodyFix = right3x.addFixture(Geometry.createRectangle(2.0, 1.0));
        bodyFix = right5x.addFixture(Geometry.createRectangle(2.0, 1.0));

        leftWall.addFixture(Geometry.createRectangle(50, 1.0));
        rightWall.addFixture(Geometry.createRectangle(50, 1.0));

        // Give slot walls structure
        slotWallOne.addFixture(Geometry.createRectangle(1.5, .125));
        slotWallTwo.addFixture(Geometry.createRectangle(1.5, .125));
        slotWallThree.addFixture(Geometry.createRectangle(1.5, .125));
        slotWallFour.addFixture(Geometry.createRectangle(1.5, .125));
        slotWallFive.addFixture(Geometry.createRectangle(1.5, .125));
        slotWallSix.addFixture(Geometry.createRectangle(1.5, .125));
        slotWallSeven.addFixture(Geometry.createRectangle(1.5, .125));
        slotWallEight.addFixture(Geometry.createRectangle(1.5, .125));
        slotWallNine.addFixture(Geometry.createRectangle(1.5, .125));
        slotWallTen.addFixture(Geometry.createRectangle(1.5, .125));
        slotWallEleven.addFixture(Geometry.createRectangle(1.5, .125));
        slotWallTwelve.addFixture(Geometry.createRectangle(1.5, .125));

        // give slot labels structure (width / height numbers dont do nuttin)
        left5xLabel.addFixture(Geometry.createRectangle(.2, .2));
        left3xLabel.addFixture(Geometry.createRectangle(.2, .2));
        left1xLabel.addFixture(Geometry.createRectangle(.2, .2));
        left08xLabel.addFixture(Geometry.createRectangle(.2, .2));
        left06xLabel.addFixture(Geometry.createRectangle(.2, .2));
        left04xLabel.addFixture(Geometry.createRectangle(.2, .2));
        middle02xLabel.addFixture(Geometry.createRectangle(.2, .2));
        right04xLabel.addFixture(Geometry.createRectangle(.2, .2));
        right06xLabel.addFixture(Geometry.createRectangle(.2, .2));
        right08xLabel.addFixture(Geometry.createRectangle(.2, .2));
        right1xLabel.addFixture(Geometry.createRectangle(.2, .2));
        right3xLabel.addFixture(Geometry.createRectangle(.2, .2));
        right5xLabel.addFixture(Geometry.createRectangle(.2, .2));
    }

    // rotating the walls and slots
    private void rotateWallsAndSlots() {

        // Make walls and slots vertical
        leftWall.rotate(Math.toRadians(90));
        rightWall.rotate(Math.toRadians(90));
        slotWallOne.rotate(Math.toRadians(90));
        slotWallTwo.rotate(Math.toRadians(90));
        slotWallThree.rotate(Math.toRadians(90));
        slotWallFour.rotate(Math.toRadians(90));
        slotWallFive.rotate(Math.toRadians(90));
        slotWallSix.rotate(Math.toRadians(90));
        slotWallSeven.rotate(Math.toRadians(90));
        slotWallEight.rotate(Math.toRadians(90));
        slotWallNine.rotate(Math.toRadians(90));
        slotWallTen.rotate(Math.toRadians(90));
        slotWallEleven.rotate(Math.toRadians(90));
        slotWallTwelve.rotate(Math.toRadians(90));
    }

    // positioning of all the bodies (magic numbers are just coordinates)
    private void moveWorldBodies() {

        // Set Position of ground and walls
        // The walls exist just beyond the edges of the screen
        // Total distance between walls is 25 units
        left5x.translate(new Vector2(-12, -9));
        left3x.translate(new Vector2(-10, -9));
        left1x.translate(new Vector2(-8, -9));
        left08x.translate(new Vector2(-6, -9));
        left06x.translate(new Vector2(-4, -9));
        left04x.translate(new Vector2(-2, -9));
        middle02x.translate(new Vector2(0, -9));
        right04x.translate(new Vector2(2, -9));
        right06x.translate(new Vector2(4, -9));
        right08x.translate(new Vector2(6, -9));
        right1x.translate(new Vector2(8, -9));
        right3x.translate(new Vector2(10, -9));
        right5x.translate(new Vector2(12, -9));

        leftWall.translate(new Vector2(-13, 4.5));
        rightWall.translate(new Vector2(13, 4.5));

        slotWallOne.translate(new Vector2(-11, -7.75));
        slotWallTwo.translate(new Vector2(-9, -7.75));
        slotWallThree.translate(new Vector2(-7, -7.75));
        slotWallFour.translate(new Vector2(-5, -7.75));
        slotWallFive.translate(new Vector2(-3, -7.75));
        slotWallSix.translate(new Vector2(-1, -7.75));
        slotWallSeven.translate(new Vector2(1, -7.75));
        slotWallEight.translate(new Vector2(3, -7.75));
        slotWallNine.translate(new Vector2(5, -7.75));
        slotWallTen.translate(new Vector2(7, -7.75));
        slotWallEleven.translate(new Vector2(9, -7.75));
        slotWallTwelve.translate(new Vector2(11, -7.75));

        left5xLabel.translate(new Vector2(-12.65, -8.5));
        left3xLabel.translate(new Vector2(-10.92, -8.5));
        left1xLabel.translate(new Vector2(-8.92, -8.5));
        left08xLabel.translate(new Vector2(-6.92, -8.5));
        left06xLabel.translate(new Vector2(-4.92, -8.5));
        left04xLabel.translate(new Vector2(-2.92, -8.5));
        middle02xLabel.translate(new Vector2(-0.92, -8.5));
        right04xLabel.translate(new Vector2(1.08, -8.5));
        right06xLabel.translate(new Vector2(3.08, -8.5));
        right08xLabel.translate(new Vector2(5.08, -8.5));
        right1xLabel.translate(new Vector2(7.08, -8.5));
        right3xLabel.translate(new Vector2(9.08, -8.5));
        right5xLabel.translate(new Vector2(10.90, -8.5));
    }

    // setting the mass of all the bodies (INFINITE = immovable)
    private void setWorldBodiesMass() {

        // Set mass of ground
        left5x.setMass(MassType.INFINITE);
        left3x.setMass(MassType.INFINITE);
        left1x.setMass(MassType.INFINITE);
        left08x.setMass(MassType.INFINITE);
        left06x.setMass(MassType.INFINITE);
        left04x.setMass(MassType.INFINITE);
        middle02x.setMass(MassType.INFINITE);
        right04x.setMass(MassType.INFINITE);
        right06x.setMass(MassType.INFINITE);
        right08x.setMass(MassType.INFINITE);
        right1x.setMass(MassType.INFINITE);
        right3x.setMass(MassType.INFINITE);
        right5x.setMass(MassType.INFINITE);

        // Set Mass of walls
        leftWall.setMass(MassType.INFINITE);
        rightWall.setMass(MassType.INFINITE);

        // Set mass of slots
        slotWallOne.setMass(MassType.INFINITE);
        slotWallTwo.setMass(MassType.INFINITE);
        slotWallThree.setMass(MassType.INFINITE);
        slotWallFour.setMass(MassType.INFINITE);
        slotWallFive.setMass(MassType.INFINITE);
        slotWallSix.setMass(MassType.INFINITE);
        slotWallSeven.setMass(MassType.INFINITE);
        slotWallEight.setMass(MassType.INFINITE);
        slotWallNine.setMass(MassType.INFINITE);
        slotWallTen.setMass(MassType.INFINITE);
        slotWallEleven.setMass(MassType.INFINITE);
        slotWallTwelve.setMass(MassType.INFINITE);

        // set mass of slot labels
        left5xLabel.setMass(MassType.INFINITE);
        left3xLabel.setMass(MassType.INFINITE);
        left1xLabel.setMass(MassType.INFINITE);
        left08xLabel.setMass(MassType.INFINITE);
        left06xLabel.setMass(MassType.INFINITE);
        left04xLabel.setMass(MassType.INFINITE);
        middle02xLabel.setMass(MassType.INFINITE);
        right04xLabel.setMass(MassType.INFINITE);
        right06xLabel.setMass(MassType.INFINITE);
        right08xLabel.setMass(MassType.INFINITE);
        right1xLabel.setMass(MassType.INFINITE);
        right3xLabel.setMass(MassType.INFINITE);
        right5xLabel.setMass(MassType.INFINITE);

        // make slot labels non-colliding
        left5xLabel.setEnabled(false);
        left3xLabel.setEnabled(false);
        left1xLabel.setEnabled(false);
        left08xLabel.setEnabled(false);
        left06xLabel.setEnabled(false);
        left04xLabel.setEnabled(false);
        middle02xLabel.setEnabled(false);
        right04xLabel.setEnabled(false);
        right06xLabel.setEnabled(false);
        right08xLabel.setEnabled(false);
        right1xLabel.setEnabled(false);
        right3xLabel.setEnabled(false);
        right5xLabel.setEnabled(false);
    }

    // add all the bodies
    private void addWorldBodiesToWorld() {

        world.addBody(left5xLabel);
        world.addBody(left3xLabel);
        world.addBody(left1xLabel);
        world.addBody(left08xLabel);
        world.addBody(left06xLabel);
        world.addBody(left04xLabel);
        world.addBody(middle02xLabel);
        world.addBody(right04xLabel);
        world.addBody(right06xLabel);
        world.addBody(right08xLabel);
        world.addBody(right1xLabel);
        world.addBody(right3xLabel);
        world.addBody(right5xLabel);
        world.addBody(left5x);
        world.addBody(left3x);
        world.addBody(left1x);
        world.addBody(left08x);
        world.addBody(left06x);
        world.addBody(left04x);
        world.addBody(middle02x);
        world.addBody(right04x);
        world.addBody(right06x);
        world.addBody(right08x);
        world.addBody(right1x);
        world.addBody(right3x);
        world.addBody(right5x);
        world.addBody(leftWall);
        world.addBody(rightWall);
        world.addBody(slotWallOne);
        world.addBody(slotWallTwo);
        world.addBody(slotWallThree);
        world.addBody(slotWallFour);
        world.addBody(slotWallFive);
        world.addBody(slotWallSix);
        world.addBody(slotWallSeven);
        world.addBody(slotWallEight);
        world.addBody(slotWallNine);
        world.addBody(slotWallTen);
        world.addBody(slotWallEleven);
        world.addBody(slotWallTwelve);
    }

    // send a ball with a specified color
    public void sendBall(Color color, Double balance) {

        SimulationBody ball = new SimulationBody(color, balance);
        ball.addFixture(Geometry.createCircle(.5), 1.0, .2, .5);
        ball.setMass(MassType.NORMAL);
        ball.setGravityScale(2);
        ball.translate(
                getBallDropPosition()); // issue with ball getting stuck if hitting spoke dead on.
        // Offset can be as small as .01
        world.addBody(ball);
        this.ballCtr++;
    }

    // send a ball with a random color
    public void sendBall(Double balance) {

        SimulationBody ball = new SimulationBody(balance);
        ball.addFixture(Geometry.createCircle(.5), 1.0, .2, .5);
        ball.setMass(MassType.NORMAL);
        ball.setGravityScale(2);
        ball.translate(
                getBallDropPosition()); // issue with ball getting stuck if hitting spoke dead on.
        // Offset can be as small as .01
        world.addBody(ball);
        this.ballCtr++;
    }

    // randomize the ball drop position
    private Vector2 getBallDropPosition() {

        Random random = new Random();
        Double randomDouble = -2.49 + (4.98 * random.nextDouble());

        // These values will cause and issue will the ball getting stuck
        if (randomDouble.equals(1.25) || randomDouble.equals(0.00) || randomDouble.equals(1.25)) {
            randomDouble += .01;
        }
        Vector2 ballPosition = new Vector2(randomDouble, 9.5);

        return ballPosition;
    }

    // creates and adds the spokes
    private void setUpSpokes() {

        // craft the spokes from stone
        for (int i = 0; i < 33; i++) {
            SimulationBody spoke = new SimulationBody(Color.BLACK);
            spoke.addFixture(Geometry.createCircle(.4));
            spoke.setMass(MassType.INFINITE);
            spoke.setGravityScale(0);
            spokes.add(spoke);
        }

        moveSpokesIntoPosition();

        // add all the spokes to the world
        for (SimulationBody spoke : spokes) {
            world.addBody(spoke);
        }
    }

    // moves the spokes into their positions
    private void moveSpokesIntoPosition() {

        // first row of spokes
        spokes.get(0).translate(new Vector2(-2.50, 6));
        spokes.get(1).translate(new Vector2(0, 6));
        spokes.get(2).translate(new Vector2(2.50, 6));

        // second row of spokes
        spokes.get(3).translate(new Vector2(-3.75, 4));
        spokes.get(4).translate(new Vector2(-1.25, 4));
        spokes.get(5).translate(new Vector2(1.25, 4));
        spokes.get(6).translate(new Vector2(3.75, 4));

        // third row of spokes
        spokes.get(7).translate(new Vector2(-5, 2));
        spokes.get(8).translate(new Vector2(-2.50, 2));
        spokes.get(9).translate(new Vector2(0, 2));
        spokes.get(10).translate(new Vector2(2.50, 2));
        spokes.get(11).translate(new Vector2(5, 2));

        // fourth row of spokes
        spokes.get(12).translate(new Vector2(-6.25, 0));
        spokes.get(13).translate(new Vector2(-3.75, 0));
        spokes.get(14).translate(new Vector2(-1.25, 0));
        spokes.get(15).translate(new Vector2(1.25, 0));
        spokes.get(16).translate(new Vector2(3.75, 0));
        spokes.get(17).translate(new Vector2(6.25, 0));

        // fifth row of spokes
        spokes.get(18).translate(new Vector2(-7.50, -2));
        spokes.get(19).translate(new Vector2(-5, -2));
        spokes.get(20).translate(new Vector2(-2.50, -2));
        spokes.get(21).translate(new Vector2(0, -2));
        spokes.get(22).translate(new Vector2(2.50, -2));
        spokes.get(23).translate(new Vector2(5, -2));
        spokes.get(24).translate(new Vector2(7.50, -2));

        // sixth row of spokes
        spokes.get(25).translate(new Vector2(-8.75, -4));
        spokes.get(26).translate(new Vector2(-6.25, -4));
        spokes.get(27).translate(new Vector2(-3.75, -4));
        spokes.get(28).translate(new Vector2(-1.25, -4));
        spokes.get(29).translate(new Vector2(1.25, -4));
        spokes.get(30).translate(new Vector2(3.75, -4));
        spokes.get(31).translate(new Vector2(6.25, -4));
        spokes.get(32).translate(new Vector2(8.75, -4));
    }

    /* (non-Javadoc)
     * @see org.dyn4j.samples.framework.SimulationFrame#initializeCamera(org.dyn4j.samples.framework.Camera)
     */
    @Override
    protected void initializeCamera(Camera camera) {
        super.initializeCamera(camera);
        camera.scale = 30.0;
        camera.offsetY = -30;
    }

    public Double getWinnings() {
        return winnings;
    }
}
