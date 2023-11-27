import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;

import java.util.ArrayList;

public class ghost {

    /**
     * This class represents a ghost object.
     * @author Ken Liem
     */
    private final Image DEFAULT_GHOST = new Image("res/ghostRed.png");
    private final Image GHOST;
    private final Image FRENZYGHOST = new Image("res/ghostFrenzy.png");
    protected static final String[] DIRECTIONS = new String[]{"LEFT", "RIGHT", "UP", "DOWN"};
    private final double FRENZY_SPEED = 0.5;
    /**
     * Value for the ghost if it eaten
     */
    public final static int VALUE = 30;
    private Point coordinates;
    private double speed = 0;
    private final Point ORIGINAL_COORDINATES;
    private boolean isFrenzy = false;
    private int frames = 0;
    private final static int MAX_FRENZY_FRAMES = 1000;
    private boolean isDead = false;

    /**
     * Constructor class for a custom ghost
     * @param coordinates the starting position for a ghost
     * @param speed how fast the ghost is going on screen per pixels per frame
     * @param ghost the image the ghost
     */
    public ghost(Point coordinates, double speed, Image ghost) {
        this.coordinates = coordinates;
        ORIGINAL_COORDINATES = coordinates;
        this.GHOST = ghost;
        this.speed = speed;
    }

    /**
     * Constructor class for a stationary ghost
     * @param coordinates the starting position for a ghost
     */
    public ghost(Point coordinates) {
        this.coordinates = coordinates;
        ORIGINAL_COORDINATES = coordinates;
        GHOST = DEFAULT_GHOST;
    }


    /**
     * Sets a new coordinates for the ghost
     * @param coordinates the new set of coordinates;
     */
    public void setCoordinates(Point coordinates) {
        this.coordinates = coordinates;
    }

    /** Gets the ghost at that  coordinate
     *
     * @return the current coordinates the ghost is at
     */
    public Point getCoordinates() {
        return coordinates;
    }


    /**
     * Draws the ghost's image at that coordinate
     * @param coordinates the coordinates where the ghost needs to be drawn
     */

    public void drawGhost(Point coordinates) {
        GHOST.drawFromTopLeft(coordinates.x, coordinates.y);
    }

    /**
     * Draws the image of a frenzy ghost at that location
     * @param coordinates the coordinates where the ghost needs to be drawn
     */
    public void drawFrenzyGhost(Point coordinates){
        FRENZYGHOST.drawFromTopLeft(coordinates.x, coordinates.y);
    }

    /**
     * Makes a rectangle of the ghost's image at it's current coordinate and returns it
     * @return the rectangle of the ghost's image at it's current position
     */
    public Rectangle ghostRectangle(){
        return new Rectangle(coordinates, GHOST.getWidth(), GHOST.getHeight());
    }
    /**
     * Makes a rectangle of the ghost's frenzy image at it's current coordinate and returns it
     * @return the rectangle of the ghost's frenzy image at it's current position
     */
    public Rectangle frenzyGhostRectangle(){
        return new Rectangle(coordinates, FRENZYGHOST.getWidth(), FRENZYGHOST.getHeight());
    }

    public void move(ArrayList<wall> walls){

    }
    /**
     * Turns on frenzyMode for the ghost and calls Frenzy Speed to change the speed of the ghost
     */
    public void FrenzyMode() {
        isFrenzy = true;
        FrenzySpeed();
    }

    /**
     * Increase the frames that the ghost have been in frenzy mode and checks if they have been in frenzy mode
     * for long enough, depending on the MAX_FRENZY_FRAMES
     */
    public void increaseFrenzyFrames(){
        frames += 1;
        if (frames == MAX_FRENZY_FRAMES){
            //Reset the coordinates if they were eaten by pacman
            if (isDead){
                coordinates = ORIGINAL_COORDINATES;
            }
            isFrenzy = false;
            isDead = false;
            frames = 0;
            FrenzySpeed();


        }
    }

    /**
     * Changes the speed based on whether the ghosts are in frenzy or not
     */
    public void FrenzySpeed() {
        if (isFrenzy()) {
            speed = speed - FRENZY_SPEED;
        } else {
            speed += FRENZY_SPEED;
        }

    }

    /**
     * Checks if the ghost is in frenzy or not
     * @return if the ghost is or isn't in frenzy
     */
    public boolean isFrenzy() {
        return isFrenzy;
    }
    /**
     * Checks if the ghosts is dead
     * @return if the ghost is or isn't dead
     */
    public boolean isDead() {
        return isDead;
    }

    /**
     * Set the ghost to be dead or not dead;
     * @param dead to indicate whether they are dead or not
     */
    public void setDead(boolean dead) {
        isDead = dead;
    }

    /**
     * Checks if the ghost has collided into any walls in the current game
     * @param walls an arraylist of walls
     * @param ghostRectangle the rectangle of the ghost that we are checking if they have collided or not
     * @return did the ghost collide into a wall
     */
    public boolean wallCollided(ArrayList<wall> walls, Rectangle ghostRectangle){
        Rectangle wallRectangle;
        for (wall w : walls) {
            wallRectangle = w.getRectangle();
            if (wallRectangle.intersects(ghostRectangle)) {
                return true;
            }
        }
        return false;
    }


    /**
     * Calculates the next point that the ghost should be in the game
     * @param Direction
     * @return the next point the ghost is going to be in
     */

    public Point calculate_nextPoint(String Direction) {
        if (Direction == DIRECTIONS[0]) {
           return new Point(getCoordinates().x - speed, getCoordinates().y);


        } else if (Direction == DIRECTIONS[1]) {
            return new Point(getCoordinates().x + speed, getCoordinates().y);


        } else if (Direction == DIRECTIONS[2]) {
            return new Point(getCoordinates().x, getCoordinates().y - speed);


        } else {
            return new Point(getCoordinates().x, getCoordinates().y + speed);


        }

    }

    /**
     * Resets the position of the ghost to the starting position
     */
    public void resetPosition(){
        coordinates = ORIGINAL_COORDINATES;
    }

}
