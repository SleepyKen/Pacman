import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;
import java.util.Random;
import java.util.ArrayList;

/**
 * Represents the pink ghost object and it's methods
 * @author Ken Liem
 * @version 1.0
 */
public class pinkGhost extends ghost {
    private final static Image GHOST = new Image("res/ghostPink.png");
    private String currentDirection;
    private final static double SPEED = 3;
    private Random randomizer = new Random();

    /**
     * Constructor for the pink ghost and selects a random direction at creation of the object
     * @param coordinates the starting position of the pink ghost
     */
    public pinkGhost(Point coordinates) {
        super(coordinates, SPEED, GHOST);
        //Selects a random direction out of
        currentDirection = DIRECTIONS[randomizer.nextInt(DIRECTIONS.length)];
    }

    /**
     * Moves the ghost to the next position and changes the direction if it collides with the wall
     * @param walls the array list of the current walls present within the game
     */
    public void move(ArrayList<wall> walls) {
        //Gets the next coordinate point and get the rectangle surrounding that point
        Rectangle ghostRectangle;
        Rectangle nextRectangle;
        Point coordinates = calculate_nextPoint(currentDirection);
        ghostRectangle = new Rectangle(coordinates.x, coordinates.y, GHOST.getWidth(), GHOST.getHeight());
        boolean success = false;
        //If the ghost collides with a wall
            if (wallCollided(walls ,ghostRectangle)) {
                String Direction;
                //While we haven't found a valid direction the ghost can move in
                while (!success) {
                    //Select a new direction and calculate the next point and rectangle using the direction
                    Direction = DIRECTIONS[randomizer.nextInt(DIRECTIONS.length)];
                    coordinates = calculate_nextPoint(Direction);
                    nextRectangle= new Rectangle(coordinates.x, coordinates.y, GHOST.getWidth(), GHOST.getHeight());
                    //If the new direction doesn't collide with a wall or is the exact direction
                    if (!currentDirection.equalsIgnoreCase(Direction) && !wallCollided(walls ,nextRectangle)) {
                        currentDirection = Direction;
                        success = true;
                    }
                }
            }

        //Get the coordinates of the new directions and set it as the new coordinates
        coordinates = calculate_nextPoint(currentDirection);
        setCoordinates(coordinates);
    }





}
