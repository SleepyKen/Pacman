import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;

import java.util.ArrayList;

/**
 * This class represents the blue ghost object and it's methods
 * @author Ken Liem
 * @version 1.0
 */
public class blueGhost extends ghost {
    private final static Image GHOST = new Image("res/ghostBlue.png");
    private String currentDirection = DIRECTIONS[3];
    private final static double SPEED = 2.0;

    /**
     * Constructor for the blue ghost
     * @param coordinates, the coordinates of the starting position
     */
    public blueGhost(Point coordinates) {
        super(coordinates, SPEED, GHOST);
    }


    /**
     * Moves the ghost up and down to the next position and changes it's direction if it collides with any walls
     *
     * @param walls list of walls present within the game
     */
    public void move(ArrayList<wall> walls) {
        //Firstly calculate the next position the ghost is going to be in
        double nextY = getCoordinates().y + SPEED;
        Rectangle ghostRectangle = new Rectangle(getCoordinates().x, nextY, GHOST.getWidth(), GHOST.getHeight());
        //Then check if the ghost is going to collide with a wall
            if (wallCollided(walls, ghostRectangle)) {
                //Change the direction if it does
                if (currentDirection == DIRECTIONS[3]){
                    currentDirection = DIRECTIONS[2];

                }
                else{
                    currentDirection = DIRECTIONS[3];
                }


            }

        //Calculate the next position based on it's current direction
        Point coordinates = calculate_nextPoint(currentDirection);
        setCoordinates(coordinates);
    }



}
