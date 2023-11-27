import bagel.util.Point;
import bagel.Image;
import bagel.util.Rectangle;

import java.util.ArrayList;

/**
 * Represents the red ghost object and it's methods
 * @author Ken Liem
 * @version 1.0
 */
public class redGhost extends ghost{
    private final static Image GHOST = new Image("res/ghostRed.png");
    private final static double speed = 1;
    private String currentDirection = DIRECTIONS[1];

    /**
     * Constructor for the redGhost
     * @param coordinates the starting position of the red ghost
     */
    public redGhost(Point coordinates) {
        super(coordinates ,speed, GHOST);
    }

    /**
     * Moves the red ghost left and right based on it's speed and changes it's direction if it collides with a wall
     * @param walls Array list of walls present within the game
     */
    public void move(ArrayList<wall> walls) {
        //Calculates the next x-coordinate that the ghost is going to move to
        double nextX = getCoordinates().x + speed;
        Rectangle ghostRectangle = new Rectangle(nextX, getCoordinates().y, GHOST.getWidth(), GHOST.getHeight());
        //Changes the direction to the opposite direction if it collides with a wall
            if (wallCollided(walls, ghostRectangle)) {
                if (currentDirection == DIRECTIONS[0]){
                    currentDirection = DIRECTIONS[1];
                }
                else{
                    currentDirection = DIRECTIONS[0];
                }


            }
        //sets a new coordinate after determining the current direction
        Point coordinates = calculate_nextPoint(currentDirection);
        setCoordinates(coordinates);
    }




}
