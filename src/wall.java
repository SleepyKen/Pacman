import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;

/**
 * This class represents a wall object and it's methods
 * @author Ken Liem
 * @version 1.0
 */
public class wall extends stationary{

    private final static Image WALL = new Image("res/wall.png");
    /**
     * Constructor for wall
     * @param coordinates
     */
    public wall(Point coordinates) {
        super(coordinates, WALL);
    }

}


