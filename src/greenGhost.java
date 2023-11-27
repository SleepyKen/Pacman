import bagel.util.Point;
import bagel.Image;
import bagel.util.Rectangle;
import java.util.ArrayList;
import java.util.Random;

/**
 * Represents the green ghost object and it's methods
 * @author Ken Liem
 * @version 1.0
 */
public class greenGhost extends ghost {

    private final static Image GHOST = new Image("res/ghostGreen.png");
    private final static double speed = 4;
    private String currentDirection;
    private final String[] TYPE_OF_DIRECTIONS = {"Vertical, Horizontal"};
    private final String DIRECTION_TYPE;
    private Random randomizer = new Random();

    /**
     * Constructor for the Green Ghost
     * Also randomises which type of direction the ghost is going to move in for the current game
     * @param coordinates for the starting position of the ghost
     *
     */
    public greenGhost(Point coordinates) {
        super(coordinates, speed, GHOST);
        DIRECTION_TYPE = TYPE_OF_DIRECTIONS[randomizer.nextInt(TYPE_OF_DIRECTIONS.length)];
        if (DIRECTION_TYPE == TYPE_OF_DIRECTIONS[0]) {
            currentDirection = DIRECTIONS[3];

        } else {
            currentDirection = DIRECTIONS[1];
        }
    }

    /**
     * Moves the ghost to the next position and changes to the opposite direction if it collides with the wall
     * @param walls list of walls present within the game
     */
    public void move(ArrayList<wall> walls) {
        //Firstly generate a new rectangle based on the next position the ghost is going to be in
        Rectangle ghostRectangle;
        if (DIRECTION_TYPE.equalsIgnoreCase(TYPE_OF_DIRECTIONS[0])) {
            ghostRectangle = new Rectangle(getCoordinates().x, getCoordinates().y + speed, GHOST.getWidth(), GHOST.getHeight());
        } else
            ghostRectangle = new Rectangle(getCoordinates().x + speed, getCoordinates().y, GHOST.getWidth(), GHOST.getHeight());
    //Checks if it collided with any wall in it's next movement, if so, change it's direction to the opposite direction
        if (wallCollided(walls, ghostRectangle)){
            if (currentDirection == DIRECTIONS[0]) {
                currentDirection = DIRECTIONS[1];


            } else if (currentDirection == DIRECTIONS[1]) {
                currentDirection = DIRECTIONS[0];


            } else if (currentDirection == DIRECTIONS[2]) {
                currentDirection = DIRECTIONS[3];


            } else {
                currentDirection = DIRECTIONS[2];


            }


            }
    //set the new coordinates based on the next Point
        Point coordinates = calculate_nextPoint(currentDirection);
        setCoordinates(coordinates);

        }




}