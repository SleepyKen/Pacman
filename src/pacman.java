import bagel.DrawOptions;
import bagel.Font;
import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * This class represents the pacman object
 *
 * @author Ken Liem
 * @version 1.0
 */
public class pacman {
    private final static Image PACMAN_CLOSED = new Image("res/pac.png");
    private final static Image PACMAN_OPEN = new Image("res/pacOpen.png");
    private boolean isOpened;
    private final DrawOptions options = new DrawOptions();
    private static final int STARTING_LIVES = 3;
    private static final String[] DIRECTIONS = new String[]{"LEFT", "RIGHT", "UP", "DOWN"};
    private int score;
    private int lives;
    private Point coordinates;
    private String currentDirection;
    private int frames = 0;
    private final Image HEART = new Image("res/heart.png");
    private double MOVEMENT = 3.0;
    public final int WINNINGSCORE = 800;
    private final Point HEARTINITAL = new Point(900, 10);
    private final Point SCORE_TEXT = new Point(25, 25);
    private final Font SCORE_FONT = new Font("res/FSO8BITR.TTF", 20);
    private final String SCORE = "Score ";
    private final double[] DIRECTIONVALUE = new double[]{Math.PI, 0, Math.PI * 1.5, Math.PI * 0.5};
    private final Point HEART_OFFSET = new Point(30, 0);
    private final int FRENZY_MODE_MAX = 1000;
    private int frenzyFrames = 0;
    private final double FRENZY_SPEED = 1;

    public boolean isFrenzy() {
        return frenzy;
    }

    private boolean frenzy = false;


    private final Point initalPoint;

    /**
     * Gets the current Coordinates
     *
     * @return coordinates
     */
    public Point getCoordinates() {
        return coordinates;
    }

    /**
     * Sets new Coordinates
     */
    public void setCoordinates(Point coordinates) {
        this.coordinates = coordinates;
    }

    //Sets the current direction
    public void setCurrentDirection(String currentDirection) {
        this.currentDirection = currentDirection;
    }

    /**
     * Gets the current Score
     *
     * @return coordinates
     */
    public int getScore() {
        return score;
    }


    /**
     * Gets the current lives
     *
     * @return lives
     */
    public int getLives() {
        return lives;
    }

    /**
     * Sets new live score
     */
    public void setLivesLost(int lives) {
        this.lives = lives;
    }

    /**
     * Gets the current movement value
     *
     * @return Movement
     */
    public double getMOVEMENT() {
        return MOVEMENT;
    }

    public pacman(Point coordinates, boolean isOpened) {
        this.coordinates = coordinates;
        this.isOpened = isOpened;
        currentDirection = DIRECTIONS[1];
        lives = STARTING_LIVES;
        score = 0;
        initalPoint = coordinates;
    }

    /**
     * Draws the image of pacman based on the direction and the state of his mouth
     */
    public void drawPacman() {
        //Checks whether enough frames has passed for pacman's mouth change state
        if (frames == 15) {
            //sets it to the other state that isn't true
            if (isOpened) {
                isOpened = false;
            } else {
                isOpened = true;
            }
            //resets the cycle
            frames = 0;
        }

        //Checks which direction pacman needs to face
        for (int i = 0; i < DIRECTIONS.length; i++) {
            if (DIRECTIONS[i].equalsIgnoreCase(currentDirection)) {
                //Checks whether pacman's mouth is opened or not
                if (!isOpened) {
                    PACMAN_CLOSED.drawFromTopLeft(coordinates.x, coordinates.y, options.setRotation(DIRECTIONVALUE[i]));
                } else {
                    PACMAN_OPEN.drawFromTopLeft(coordinates.x, coordinates.y, options.setRotation(DIRECTIONVALUE[i]));
                }
            }
        }
        frames += 1;

        if (frenzy) {
            frenzyFrames += 1;
            //Turns off the Frenzy mode once the time limit has been reached
            if (frenzyFrames == FRENZY_MODE_MAX) {
                frenzy = false;
                frenzyFrames = 0;
                FrenzySpeed();
            }
        }

        renderLives();
        renderScore();
    }

    /**
     * Checks whether the player has collided with any ghost on the screen
     *
     * @param ghosts to check if any ghost has been collided by pacman
     * @return true or false
     */
    public boolean ghostCollided(ArrayList<ghost> ghosts) {
        Rectangle ghost;
        Rectangle player = playerRectangle();
        ;
        //Gets the rectangle surrounding the ghost and the player

        //For every ghost, check if the ghost's rectangle overlaps with the player
        for (ghost g : ghosts) {
            if (!g.isDead()) {
                if (frenzy) {
                    ghost = g.frenzyGhostRectangle();
                } else {
                    ghost = g.ghostRectangle();
                }
                //If their rectangles intersect;
                if (ghost.intersects(player)) {
                    //Kills the ghost if it is frenzy mode
                    if (frenzy == true) {
                        g.setDead(true);
                    }
                    else{
                        g.resetPosition();
                    }
                    return true;

                }
            }
        }

        return false;
    }

    /**
     * Checks whether the player will collide with the wall
     *
     * @param walls checks if any of the walls on the screen
     * @param nextX used to check for the next x position that pacman will be in order to check if it
     * won't be in the wall
     * @param nextY used to check for the next y position that pacman will be in order to check if it
     * won't be in the wall
     * @return true or false
     */
    public boolean wallCollided(ArrayList<wall> walls, double nextX, double nextY) {

        Rectangle wall;
        Rectangle player;
        //Checks for which image should the rectangle be drawn for;
        if (!isOpened) {
            player = new Rectangle(nextX, nextY, PACMAN_CLOSED.getWidth(), PACMAN_CLOSED.getHeight());

        } else {
            player = new Rectangle(nextX, nextY, PACMAN_CLOSED.getWidth(), PACMAN_CLOSED.getHeight());

        }


        //For every wall, check if the wall rectangle overlaps with the player
        for (wall w : walls) {
            wall = w.getRectangle();
            //If the wall's rectangle intersects with the player
            if (wall.intersects(player)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks whether the player has collided with any dots on the screen
     *
     * @param dots to check if any dots has been collided by pacman
     * @return true or false
     */
    //Checks whether the player has collided with the dot
    public boolean dotCollided(ArrayList<dot> dots) {
        Rectangle dot;
        Rectangle player;
        //Gets the rectangle surrounding the dot and the player
        player = playerRectangle();
        for (dot d : dots) {
            dot = d.getRectangle();
            //If their rectangle box intersects and if the dot is still displayed;
            if (dot.intersects(player) && d.isActive()) {
                //Removes the dot from the game
                d.setActive(false);
                return true;
            }
        }
        return false;
    }

    /**
     * Checks whether the player has collided with any cherries on the screen
     *
     * @param cherries to check if any dots has been collided by pacman
     * @return true or false
     */
    //Checks for if pacman collided with the pacman rectangle
    public boolean cherryCollided(ArrayList<cherry> cherries) {
        //If their rectangle box intersects and if the cherry is still displayed;
        for (cherry c : cherries) {
            if (c.getRectangle().intersects(playerRectangle()) && c.isActive()) {
                c.setActive(false);
                return true;

            }

        }
        return false;
    }

    /**
     * Checks whether the player has collided with any pellets on the screen
     *
     * @param pellets to check if any pellets has been collided by pacman
     * @return true or false
     */
    public boolean pelletCollided(ArrayList<pellet> pellets) {
        for (pellet p : pellets) {
            //If their rectangle box intersects and if the pellet is still displayed;
            if (p.getRectangle().intersects(playerRectangle()) && p.isActive()) {
                p.setActive(false);
                return true;

            }

        }
        return false;
    }

    /**
     * Creates the rectangle around the player
     *
     * @return rectangle
     */
    public Rectangle playerRectangle() {
        if (!isOpened) {
            return new Rectangle(coordinates, PACMAN_CLOSED.getWidth(), PACMAN_CLOSED.getHeight());

        } else {
            return new Rectangle(coordinates, PACMAN_CLOSED.getWidth(), PACMAN_CLOSED.getHeight());

        }
    }

    /**
     * Adds value to score to the player
     *
     * @param value
     */
    public void addScore(int value) {
        score += value;

    }

    /**
     * Renders the current score for the player
     */
    private void renderScore() {
        SCORE_FONT.drawString(SCORE + score, SCORE_TEXT.x, SCORE_TEXT.y);
    }

    /**
     * Renders the life for the player
     */
    private void renderLives() {
        //For every life that the player has left
        for (int i = 0; i < lives; i++) {
            HEART.drawFromTopLeft(HEARTINITAL.x + (HEART_OFFSET.x * i), HEARTINITAL.y);
        }
    }

    /**
     * Returns the starting position for pacman
     *
     * @return point
     */
    public Point pacmanStartingPosition() {
        return initalPoint;
    }

    /**
     * Turns on frenzy mode for pacman
     */
    public void frenzyMode() {
        frenzy = true;
        FrenzySpeed();

    }

    /**
     * Changes the speed based on whether the player is going into frenzy mode or exiting frenzy mode
     */
    public void FrenzySpeed() {
        if (isFrenzy()) {
            MOVEMENT += FRENZY_SPEED;
        } else {
            MOVEMENT -= FRENZY_SPEED;
        }
    }

    /**
     * Takes away a life from pacman and resets him to the starting position
     */
    //Resets the position and makes pacman loses a life
    public void killed() {
        coordinates = pacmanStartingPosition();
        lives -= 1;
        currentDirection = DIRECTIONS[1];
    }
}


